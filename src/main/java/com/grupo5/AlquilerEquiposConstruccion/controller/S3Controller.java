package com.grupo5.AlquilerEquiposConstruccion.controller;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.grupo5.AlquilerEquiposConstruccion.dto.CityDTO;
import com.grupo5.AlquilerEquiposConstruccion.service.impl.S3ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/file")
public class S3Controller {

    @Autowired
    private S3ServiceImpl s3ServiceImpl;

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
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile[] file, @PathVariable Integer id) throws IOException {

        List<String> imagesUrls = new ArrayList<>();

        for (MultipartFile f:
             file) {
            File convertedFile = convertMultiPartFileToFile(f);
            String fileName = generateFileName(f);
            s3ServiceImpl.uploadFile(fileName, convertedFile);
            convertedFile.delete();
            String s3Url = amazonS3.getUrl(bucketName, fileName).toString();
            imagesUrls.add(s3Url);
        }
          return ResponseEntity.ok(imagesUrls);
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
