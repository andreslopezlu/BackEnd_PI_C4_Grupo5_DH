package com.grupo5.AlquilerEquiposConstruccion.controller;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.ImageDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.service.ImageService;
import com.grupo5.AlquilerEquiposConstruccion.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private S3Service s3Service;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Autowired
    private ObjectMapper mapper;

    AWSCredentials credentials = new BasicAWSCredentials(
            "AKIAY3PLHSUJCOQE5HPE",
            "edla3tGu+0R/Yz4tUlT8eQETIKFFuN1u4Ph0md6t"
    );

//    public ImageController(@Value("${aws.s3.bucketName}") String bucketName) {
//        this.bucketName = bucketName;
//    }

    @GetMapping
    public ResponseEntity<List<ImageDTO>> getAllImages() throws NotFoundException {
        return ResponseEntity.ok(imageService.getAllImages());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<ImageDTO>> findByproduct_id(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok(imageService.findByproduct_id(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageDTO> getImageById(@PathVariable Integer id) throws NotFoundException {
        Optional<ImageDTO> imageSearch = imageService.getImageById(id);
        if (imageSearch.isPresent()) {
            return ResponseEntity.ok(imageSearch.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveImage(@RequestBody ImageDTO imageDTO) throws BadRequestException {
        return ResponseEntity.ok(imageService.saveImage(imageDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateImage(@RequestBody ImageDTO imageDTO) throws Exception{
        Optional<ImageDTO> imageSearch = imageService.getImageById(imageDTO.getId());
        if (imageSearch.isPresent()) {
            return ResponseEntity.ok(imageService.updateImage(imageDTO, imageSearch.get().getId()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image with ID: " + imageDTO.getId() + " was not found.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> eliminarImagen(@PathVariable Integer id) throws NotFoundException {
        if (imageService.getImageById(id).isPresent()) {
            String imageKey = imageService.getImageById(id).get().getTitle();
            s3Service.deleteFile(imageKey);
            imageService.deleteImageById(id);
            return ResponseEntity.ok("The image with id: " + id + " was deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image with id: " + id + " was not found.");
    }
}
