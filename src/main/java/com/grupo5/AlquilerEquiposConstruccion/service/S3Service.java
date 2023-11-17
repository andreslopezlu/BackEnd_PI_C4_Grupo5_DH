package com.grupo5.AlquilerEquiposConstruccion.service;

import com.amazonaws.services.s3.model.S3Object;

import java.io.File;

public interface S3Service {

    void uploadFile(String key, File file);

    S3Object downloadFile(String key);

    void deleteFile(String key);
}
