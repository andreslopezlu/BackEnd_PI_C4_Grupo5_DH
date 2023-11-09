package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.CategoryDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.ProductDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Category;
import com.grupo5.AlquilerEquiposConstruccion.model.Product;
import com.grupo5.AlquilerEquiposConstruccion.repository.CategoryRepository;
import com.grupo5.AlquilerEquiposConstruccion.repository.ProductRepository;
import com.grupo5.AlquilerEquiposConstruccion.service.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final Logger logger = Logger.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productsDTO = new ArrayList<>();
        for (Product product : products){
            productsDTO.add(mapper.convertValue(product,ProductDTO.class));
        }
        return productsDTO;
    }

    @Override
    public Optional<ProductDTO> getProductById(Integer id) throws NotFoundException {
        Product productFounded = productRepository.findById(id).orElseThrow(() -> new NotFoundException("The " +
                "product with the id: " + id + " was not found."));
        return Optional.ofNullable(mapper.convertValue(productFounded, ProductDTO.class));
    }

    @Override
    public ProductDTO createProduct(ProductDTO product) throws BadRequestException, NotFoundException {
        if (product.getName()==null || product.getDescription()==null || product.getSpecifications()==null || product.getCostPerDay()==null){
            throw new BadRequestException("The product has null values.");
        } else {
            Optional<CategoryDTO> category = categoryService.getCategoryById(product.getCategory_id());
            Product productCreated = mapper.convertValue(product,Product.class);
            logger.info("The product was created successfully.");
            return mapper.convertValue(productRepository.save(productCreated), ProductDTO.class);
        }
    }

    @Override
    public ProductDTO updateProduct(ProductDTO product, Integer id) throws NotFoundException {
        Optional<ProductDTO> existingProduct = getProductById(id);
        if (existingProduct.isPresent()){
            existingProduct.get().setName(product.getName());
            existingProduct.get().setDescription(product.getDescription());
            existingProduct.get().setSpecifications(product.getSpecifications());
            Product productUpdated = mapper.convertValue(existingProduct, Product.class);
            productRepository.save(productUpdated);
            logger.info("The product was updated successfully.");
        }
        return mapper.convertValue(existingProduct, ProductDTO.class);
    }

    @Override
    public void deleteProductById(Integer id) {

    }

    @Override
    public List<ProductDTO> getProductsByCategory(String name) {
        return null;
    }

    @Override
    public List<ProductDTO> getProductsByCity(String name) {
        return null;
    }

    @Override
    public List<ProductDTO> getRandomProduct() {
        return null;
    }
}
