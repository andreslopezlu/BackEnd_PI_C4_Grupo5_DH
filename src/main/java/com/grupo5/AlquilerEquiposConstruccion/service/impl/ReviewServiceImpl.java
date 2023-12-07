package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.ProductDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.ReviewDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Review;
import com.grupo5.AlquilerEquiposConstruccion.repository.ReviewRepository;
import com.grupo5.AlquilerEquiposConstruccion.service.ReviewService;
import jakarta.transaction.Transactional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service

public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    private ObjectMapper mapper;

    private final Logger logger = Logger.getLogger(CityServiceImpl.class);

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    CategoryServiceImpl categoryService;

    @Override
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        List<ReviewDTO> reviewsDTO = new ArrayList<>();
        for(Review review: reviews){
            reviewsDTO.add(mapper.convertValue(review, ReviewDTO.class));
        }
        return reviewsDTO;
    }

    @Override
    public Optional<ReviewDTO> getReviewById(Integer id) throws NotFoundException {
        Review reviewFounded = reviewRepository.findById(id).orElseThrow(() -> new NotFoundException("The " +
                "review with the id: " + id + " was not found."));
        return Optional.ofNullable(mapper.convertValue(reviewFounded, ReviewDTO.class));
    }

    @Override
    public ReviewDTO createReview(ReviewDTO review) throws BadRequestException, NotFoundException {

        Optional<Review> reviewFounded = reviewRepository.findByUser_idAndProduct_id(review.getUser().getId(), review.getProduct().getId());
        Integer productId = review.getProduct().getId();
        Review reviewCreated = null;

        if (review.getUser()==null || review.getProduct()==null || review.getReview()==null || review.getScore()==null){
            throw new BadRequestException("The review has null values.");
        } else if (reviewFounded.isEmpty()){

            Integer score = review.getScore();
            LocalDate publicationDate = LocalDate.now();
            review.setPublication_date(publicationDate);

            ProductDTO existingProduct = productService.getProductById(productId).get();

            existingProduct.setId(existingProduct.getId());
            existingProduct.setName(existingProduct.getName());
            existingProduct.setDescription(existingProduct.getDescription());
            existingProduct.setSpecifications(existingProduct.getSpecifications());
            existingProduct.setActive(existingProduct.isActive());
            existingProduct.setAvailable(existingProduct.isAvailable());
            existingProduct.setCostPerDay(existingProduct.getCostPerDay());
            existingProduct.setCategory(existingProduct.getCategory());
            existingProduct.setCity(existingProduct.getCity());

            Integer totalReviews = existingProduct.getTotalReviews();
            existingProduct.setTotalReviews(totalReviews+1);

            Integer totalScore = existingProduct.getTotalScore();
            existingProduct.setTotalScore(totalScore+score);

            ProductDTO productUpdated = mapper.convertValue(existingProduct, ProductDTO.class);
            productService.updateProduct(productUpdated, productId);

            reviewCreated = mapper.convertValue(review, Review.class);
        } else {
            throw new BadRequestException("The review already exists.");
        }
        return mapper.convertValue(reviewRepository.save(reviewCreated), ReviewDTO.class);
    }

    @Override
    public void deleteById(Integer id) throws NotFoundException {
        reviewRepository.findById(id).orElseThrow(() -> new NotFoundException("The " +
                "review with the id: " + id + " was not found."));
        reviewRepository.deleteById(id);
    }

    @Override
    public List<ReviewDTO> findByProduct_id(Integer id) throws NotFoundException {
        List<Review> reviewFounded = reviewRepository.findByProduct_id(id);
        return reviewFounded.stream()
                .map(review -> mapper.convertValue(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> findByUser_id(Integer id) throws NotFoundException {
        List<Review> reviewFounded = reviewRepository.findByUser_id(id);
        return reviewFounded.stream()
                .map(review -> mapper.convertValue(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ReviewDTO> findByUser_idAndProduct_id(Integer userId, Integer productId) throws NotFoundException {
        Review reviewFounded = reviewRepository.findByUser_idAndProduct_id(userId, productId).orElseThrow(() -> new NotFoundException("The " +
                "review for the user with the id " + userId + " and the product with the id " + productId + " was not found."));
        return Optional.ofNullable(mapper.convertValue(reviewFounded, ReviewDTO.class));
    }
}
