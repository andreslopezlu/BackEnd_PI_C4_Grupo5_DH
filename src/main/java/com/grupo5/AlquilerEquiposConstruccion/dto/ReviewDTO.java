package com.grupo5.AlquilerEquiposConstruccion.dto;

import com.grupo5.AlquilerEquiposConstruccion.model.Product;
import com.grupo5.AlquilerEquiposConstruccion.model.User;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewDTO {
    private Integer id;
    private LocalDate publication_date;
    private String review;
    private Product product;
    private User user;

    private Integer score;

    public ReviewDTO(LocalDate publication_date, String review, Product product, User user, Integer score) {
        this.publication_date = publication_date;
        this.review = review;
        this.product = product;
        this.user = user;
        this.score = score;
    }
}
