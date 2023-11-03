package com.grupo5.AlquilerEquiposConstruccion.service;

import com.grupo5.AlquilerEquiposConstruccion.model.Category;
import com.grupo5.AlquilerEquiposConstruccion.model.City;
import com.grupo5.AlquilerEquiposConstruccion.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();
    Optional<Product> getProductById(Integer id);
    Product saveProduct(Product product);
    Product updateProduct(Product product);
    void deleteProductById(Integer id);
    List<Product>getProductsByCategory(Category id);
    List<Product>getProductsByCity(City id);
    List<Product> getRandomProduct();

}
