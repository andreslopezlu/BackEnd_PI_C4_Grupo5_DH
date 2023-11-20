package com.grupo5.AlquilerEquiposConstruccion.controller;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.CityDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.ImageDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.ProductDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Image;
import com.grupo5.AlquilerEquiposConstruccion.model.Product;
import com.grupo5.AlquilerEquiposConstruccion.repository.ImageRepository;
import com.grupo5.AlquilerEquiposConstruccion.repository.ProductRepository;
import com.grupo5.AlquilerEquiposConstruccion.service.ImageService;
import com.grupo5.AlquilerEquiposConstruccion.service.ProductService;
import com.grupo5.AlquilerEquiposConstruccion.service.S3Service;
import com.grupo5.AlquilerEquiposConstruccion.service.impl.S3ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/file")
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ProductService productService;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    AWSCredentials credentials = new BasicAWSCredentials(
            "AKIAY3PLHSUJCOQE5HPE",
            "edla3tGu+0R/Yz4tUlT8eQETIKFFuN1u4Ph0md6t"
    );

    private AmazonS3 amazonS3 = AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.US_WEST_1)
            .build();

    public S3Controller(@Value("${aws.s3.bucketName}") String bucketName) {
        this.bucketName = bucketName;
    }


    @PostMapping("/upload/{id}")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile[] file, @PathVariable Integer id) throws IOException, NotFoundException, BadRequestException {

        Map<String, String> imagesData = new HashMap<>();

        for (MultipartFile f: file) {
            File convertedFile = convertMultiPartFileToFile(f);
            String fileName = generateFileName(f);
            s3Service.uploadFile(fileName, convertedFile);
            convertedFile.delete();
            String s3Url = amazonS3.getUrl(bucketName, fileName).toString();
            imagesData.put(fileName, s3Url);
        }

        for (Map.Entry<String, String> entry : imagesData.entrySet()) {
            String name = entry.getKey();
            String url = entry.getValue();
            ImageDTO imageDTO = new ImageDTO(name, url);
            imageService.saveImageByProductId(imageDTO, id);
        }

        List<ImageDTO> images = imageService.findByproduct_id(id);

        return ResponseEntity.ok(images);
    }

    private File convertMultiPartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }
}
