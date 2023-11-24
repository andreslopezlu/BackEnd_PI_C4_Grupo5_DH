package com.grupo5.AlquilerEquiposConstruccion.repository;

import com.grupo5.AlquilerEquiposConstruccion.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoritesRepository extends JpaRepository<Favorite, Integer> {
    Optional<Favorite> findByProduct_id(Integer id);
    Optional<Favorite> findByUser_id(Integer id);
 }
