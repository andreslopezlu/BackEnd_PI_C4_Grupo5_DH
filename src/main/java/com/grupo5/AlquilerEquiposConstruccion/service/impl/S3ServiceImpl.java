package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.grupo5.AlquilerEquiposConstruccion.service.S3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;

import java.io.File;


@Service
public class S3ServiceImpl implements S3Service {

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    AWSCredentials credentials = new BasicAWSCredentials(
            "AKIAY3PLHSUJCOQE5HPE",
            "edla3tGu+0R/Yz4tUlT8eQETIKFFuN1u4Ph0md6t"
    );

    AmazonS3 amazonS3 = AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.US_WEST_1)
            .build();

    public S3ServiceImpl(@Value("${aws.s3.bucketName}") String bucketName) {
        this.bucketName = bucketName;
    }


    public void uploadFile(String key, File file) {
        amazonS3.putObject(bucketName, key, file);
    }

    public void deleteFile(String key) {
        amazonS3.deleteObject(bucketName, key);
    }

//    public S3Object downloadFile(String key) {
//        return amazonS3.getObject(bucketName, key);
//    }
}