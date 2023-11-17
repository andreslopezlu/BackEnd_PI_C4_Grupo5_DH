package com.grupo5.AlquilerEquiposConstruccion.controller;

import com.grupo5.AlquilerEquiposConstruccion.dto.CityDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.ImageDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.UserDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Image;
import com.grupo5.AlquilerEquiposConstruccion.service.ImageService;
import com.grupo5.AlquilerEquiposConstruccion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping
    public ResponseEntity<List<ImageDTO>> getAllImages(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok(imageService.getAllImages());
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
            imageService.deleteImageById(id);
            return ResponseEntity.ok("The image with id: " + id + " was deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image with id: " + id + " was not found.");
    }
}
