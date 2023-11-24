package com.grupo5.AlquilerEquiposConstruccion.controller;

import com.grupo5.AlquilerEquiposConstruccion.dto.FavoriteDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<List<FavoriteDTO>> getAllFavorites() {
        return ResponseEntity.ok(favoriteService.getAllFavorites());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoriteDTO> getFavoriteById(@PathVariable Integer id) throws NotFoundException {
        Optional<FavoriteDTO> favoriteSearch = favoriteService.getFavoriteById(id);
        if (favoriteSearch.isPresent()) {
            return ResponseEntity.ok(favoriteSearch.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<FavoriteDTO> createFavorite(@RequestBody FavoriteDTO favorite) throws BadRequestException {
        return ResponseEntity.ok(favoriteService.createFavorite(favorite));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateFavorite(@RequestBody FavoriteDTO favorite) throws Exception {
        Optional<FavoriteDTO> favoriteSearch = favoriteService.getFavoriteById(favorite.getId());
        if (favoriteSearch.isPresent()) {
            return ResponseEntity.ok(favoriteService.updateFavorite(favorite));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Favorite with ID: " + favorite.getId() + " was not found.");
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFavoriteById(@PathVariable Integer id) throws NotFoundException {
        if (favoriteService.getFavoriteById(id).isPresent()) {
            favoriteService.deleteFavoriteById(id);
            return ResponseEntity.ok("The favorite with id: " + id + " was deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Favorite with id: " + id + " was not found.");
    }

    @GetMapping("/by-product/{id}")
    public ResponseEntity<FavoriteDTO> findByProduct_id(@PathVariable Integer id) throws NotFoundException {
        Optional<FavoriteDTO> favoriteSearch = favoriteService.findByProduct_id(id);
        if (favoriteSearch.isPresent()) {
            return ResponseEntity.ok(favoriteSearch.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<FavoriteDTO> findByUser_id(@PathVariable Integer id) throws NotFoundException {
        Optional<FavoriteDTO> favoriteSearch = favoriteService.findByUser_id(id);
        if (favoriteSearch.isPresent()) {
            return ResponseEntity.ok(favoriteSearch.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
