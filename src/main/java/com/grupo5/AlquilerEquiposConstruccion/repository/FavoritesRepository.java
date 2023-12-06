package com.grupo5.AlquilerEquiposConstruccion.repository;

import com.grupo5.AlquilerEquiposConstruccion.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritesRepository extends JpaRepository<Favorite, Integer> {
    List<Favorite> findByProduct_id(Integer id);
    List<Favorite> findByUser_id(Integer id);
    Optional<Favorite> findByUser_idAndProduct_id(Integer userId, Integer productId);
    void deleteByUser_idAndProduct_id(Integer userId, Integer productId);
 }
