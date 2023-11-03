package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.grupo5.AlquilerEquiposConstruccion.dto.ProductDTO;
import com.grupo5.AlquilerEquiposConstruccion.model.Product;
import com.grupo5.AlquilerEquiposConstruccion.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Optional<Product> getProductById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Product saveProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProduct(Product product) {
        return null;
    }

    @Override
    public void deleteProductById(Integer id) {

    }

    @Override
    public List<ProductDTO> getProductsByCategory(String name) {
        return null;
    }

    @Override
    public List<Product> getProductsByCity(String name) {
        return null;
    }

    @Override
    public List<Product> getRandomProduct() {
        return null;
    }
}
