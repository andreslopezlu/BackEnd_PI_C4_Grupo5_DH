package com.grupo5.AlquilerEquiposConstruccion.service;

import com.grupo5.AlquilerEquiposConstruccion.model.Image;
import java.util.List;
import java.util.Optional;

public interface ImageService {
    List<Image> getAllImages();
    Optional<Image> getImageById(Integer id);
    Image saveImage(Image image, Integer productId);
    Image updateImage(Image image, Integer productId);
    void deleteImageById(Integer id);
}
