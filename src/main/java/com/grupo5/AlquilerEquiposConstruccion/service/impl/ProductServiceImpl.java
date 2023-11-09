package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.CategoryDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.CityDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.ProductDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.ProductDTORequest;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Category;
import com.grupo5.AlquilerEquiposConstruccion.model.City;
import com.grupo5.AlquilerEquiposConstruccion.model.Product;
import com.grupo5.AlquilerEquiposConstruccion.repository.CategoryRepository;
import com.grupo5.AlquilerEquiposConstruccion.repository.ProductRepository;
import com.grupo5.AlquilerEquiposConstruccion.service.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final Logger logger = Logger.getLogger(ProductServiceImpl.class);

    @Autowired
    @Lazy
    private ProductRepository productRepository;

    @Autowired
    @Lazy
    private CategoryServiceImpl categoryService;

    @Autowired
    @Lazy
    private CityServiceImpl cityService;

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
    public ProductDTO createProduct(ProductDTORequest product) throws BadRequestException, NotFoundException {
        if (product.getName() == null || product.getDescription() == null || product.getSpecifications() == null || product.getCostPerDay() == null) {
            throw new BadRequestException("The product has null values.");
        } else {
            Product productCreated = mapper.convertValue(product, Product.class);
            Optional<CategoryDTO> category = categoryService.getCategoryById(product.getCategory_id());
            if (category.isPresent()) {
                Category categoryEntity = mapper.convertValue(category.get(), Category.class);
                productCreated.setCategory(categoryEntity);
            } else {
                throw new NotFoundException("Category not found.");
            }
            Optional<CityDTO> city = cityService.getCityById(product.getCity_id());
            if (city.isPresent()) {
                City cityEntity = mapper.convertValue(city.get(), City.class);
                productCreated.setCity(cityEntity);
            } else {
                throw new NotFoundException("City not found.");
            }
            logger.info("The product was created successfully.");
            Product savedProduct = productRepository.save(productCreated);
            return mapper.convertValue(savedProduct, ProductDTO.class);
        }
    }

    @Override
    public ProductDTO updateProduct(ProductDTORequest product, Integer id) throws NotFoundException {
        Optional<ProductDTO> existingProduct = getProductById(id);
        if (existingProduct.isPresent()){
            existingProduct.get().setName(product.getName());
            existingProduct.get().setDescription(product.getDescription());
            existingProduct.get().setSpecifications(product.getSpecifications());
            Product productUpdated = mapper.convertValue(existingProduct, Product.class);
            Optional<CategoryDTO> category = categoryService.getCategoryById(product.getCategory_id());
            if (category.isPresent()) {
                Category categoryEntity = mapper.convertValue(category.get(), Category.class);
                productUpdated.setCategory(categoryEntity);
            } else {
                throw new NotFoundException("Category not found.");
            }
            Optional<CityDTO> city = cityService.getCityById(product.getCity_id());
            if (city.isPresent()) {
                City cityEntity = mapper.convertValue(city.get(), City.class);
                productUpdated.setCity(cityEntity);
            } else {
                throw new NotFoundException("City not found.");
            }
            Product updatedProduct = productRepository.save(productUpdated);
            logger.info("The product was updated successfully.");

            return mapper.convertValue(updatedProduct, ProductDTO.class);
        } else {
            throw new NotFoundException("Product not found.");
        }
    }

    @Override
    public void deleteProductById(Integer id) throws NotFoundException {
        Optional<ProductDTO> productFounded = getProductById(id);
        if (productFounded.isPresent()) {
            Product product = mapper.convertValue(productFounded, Product.class);
            product.setActive(false);
            productRepository.save(product);
        } else {
            throw new NotFoundException("The product with ID " + id + " was not found.");
        }
    }

    @Override
    public List<ProductDTO> getProductsByCategory(String name) {

        List<Product> productsByCategory = productRepository.findByCategoryName(name);

        List<ProductDTO> productDTOList = productsByCategory.stream()
                .map(product -> mapper.convertValue(product, ProductDTO.class))
                .collect(Collectors.toList());

        return productDTOList;
    }
    @Override
    public List<ProductDTO> getProductsByCity(String name) {
        List<Product> productsByCity = productRepository.findByCityName(name);

        List<ProductDTO> productDTOList = productsByCity.stream()
                .map(product -> mapper.convertValue(product, ProductDTO.class))
                .collect(Collectors.toList());

        return productDTOList;
    }
    @Override
    public List<ProductDTO> getRandomProduct() {
        long totalProducts = productRepository.count();

        Set<Integer> randomIndexes = new HashSet<>();
        Random random = new Random();
        while (randomIndexes.size() < 10) {
            int randomIndex = random.nextInt((int) totalProducts);
            randomIndexes.add(randomIndex);
        }

        List<Product> randomProducts = productRepository.findAllById(randomIndexes);

        return randomProducts.stream()
                .map(product -> mapper.convertValue(product, ProductDTO.class))
                .collect(Collectors.toList());
    }
}
