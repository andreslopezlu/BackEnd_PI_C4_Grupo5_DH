package com.grupo5.AlquilerEquiposConstruccion.repository;

import com.grupo5.AlquilerEquiposConstruccion.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName")
    List<Product> findByCategoryName(@Param("categoryName") String categoryName);

    @Query("SELECT p FROM Product p WHERE p.city.name = :cityName")
    List<Product> findByCityName(@Param("cityName") String cityName);
}

