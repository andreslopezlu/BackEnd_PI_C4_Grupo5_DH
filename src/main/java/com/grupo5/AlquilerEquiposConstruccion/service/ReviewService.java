package com.grupo5.AlquilerEquiposConstruccion.service;

import com.grupo5.AlquilerEquiposConstruccion.dto.ReviewDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.BadRequestException;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<ReviewDTO> getAllReviews();
    Optional<ReviewDTO> getReviewById(Integer id) throws NotFoundException;
    ReviewDTO createReview(ReviewDTO review) throws BadRequestException, NotFoundException;
    void deleteReviewById(Integer id) throws NotFoundException;
    List<ReviewDTO> findByProduct_id(Integer id) throws NotFoundException;
    List<ReviewDTO> findByUser_id(Integer id) throws NotFoundException;
}
