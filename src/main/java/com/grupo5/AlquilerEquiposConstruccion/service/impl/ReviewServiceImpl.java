package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.FavoriteDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.ProductDTO;
import com.grupo5.AlquilerEquiposConstruccion.dto.ReviewDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Favorite;
import com.grupo5.AlquilerEquiposConstruccion.model.Review;
import com.grupo5.AlquilerEquiposConstruccion.repository.ReviewRepository;
import com.grupo5.AlquilerEquiposConstruccion.service.ProductService;
import com.grupo5.AlquilerEquiposConstruccion.service.ReviewService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    private ObjectMapper mapper;

    private final Logger logger = Logger.getLogger(CityServiceImpl.class);

    @Autowired
    ProductServiceImpl productService;

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
                "favorite with the id: " + id + " was not found."));
        return Optional.ofNullable(mapper.convertValue(reviewFounded, ReviewDTO.class));
    }

    @Override
    public ReviewDTO createReview(ReviewDTO review) throws BadRequestException, NotFoundException {
        if (review.getUser()==null || review.getProduct()==null || review.getReview()==null || review.getScore()==null){
            throw new BadRequestException("The review has null values.");
        } else{
            LocalDate publicationDate = LocalDate.now();
            review.setPublication_date(publicationDate);

            Integer productId = review.getProduct().getId();
            ProductDTO existingProduct = productService.getProductById(productId).get();
            Integer totalReviews = existingProduct.getTotalReviews();
            existingProduct.setTotalReviews(totalReviews+1);
            Double average_score = existingProduct.getAverage_score();


            Review reviewCreated = mapper.convertValue(review, Review.class);
            logger.info("The review was created successfully.");
            return mapper.convertValue(reviewRepository.save(reviewCreated), ReviewDTO.class);
        }
    }

    @Override
    public void deleteReviewById(Integer id) throws NotFoundException {
        reviewRepository.findById(id).orElseThrow(() -> new NotFoundException("The " +
                "review with the id: " + id + " was not found."));
        reviewRepository.deleteById(id);
    }

    @Override
    public Optional<ReviewDTO> findByProduct_id(Integer id) throws NotFoundException {
        Review reviewFounded = reviewRepository.findByProduct_id(id).orElseThrow(() -> new NotFoundException("The " +
                "review for the product with the id: " + id + " was not found."));
        return Optional.ofNullable(mapper.convertValue(reviewFounded, ReviewDTO.class));
    }

    @Override
    public Optional<ReviewDTO> findByUser_id(Integer id) throws NotFoundException {
        Review reviewFounded = reviewRepository.findByUser_id(id).orElseThrow(() -> new NotFoundException("The " +
                "review for the user with the id: " + id + " was not found."));
        return Optional.ofNullable(mapper.convertValue(reviewFounded, ReviewDTO.class));
    }
}
