package com.grupo5.AlquilerEquiposConstruccion.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;

import java.io.File;

@Service
public class S3Service {


    private final AmazonS3 amazonS3;
    private final String bucketName;


    public S3Service(AmazonS3 amazonS3, @Value("${aws.s3.bucketName}") String bucketName) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }


    public void uploadFile(String key, File file) {
        amazonS3.putObject(bucketName, key, file);
    }


    public void deleteFile(String key) {
        amazonS3.deleteObject(bucketName, key);
    }


    public S3Object downloadFile(String key) {
        return amazonS3.getObject(bucketName, key);
    }
}