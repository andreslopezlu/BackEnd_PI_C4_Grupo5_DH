package com.grupo5.AlquilerEquiposConstruccion.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

public class ReservationDTO {
    private Integer id;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate check_in_date;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate checkout_date;
    private String comments;

    public ReservationDTO() {
    }

    public ReservationDTO(Integer id, LocalDate check_in_date, LocalDate checkout_date, String comments) {
        this.id = id;
        this.check_in_date = check_in_date;
        this.checkout_date = checkout_date;
        this.comments = comments;
    }

    public ReservationDTO(LocalDate check_in_date, LocalDate checkout_date, String comments) {
        this.check_in_date = check_in_date;
        this.checkout_date = checkout_date;
        this.comments = comments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getCheck_in_date() {
        return check_in_date;
    }

    public void setCheck_in_date(LocalDate check_in_date) {
        this.check_in_date = check_in_date;
    }

    public LocalDate getCheckout_date() {
        return checkout_date;
    }

    public void setCheckout_date(LocalDate checkout_date) {
        this.checkout_date = checkout_date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "id=" + id +
                ", check_in_date=" + check_in_date +
                ", checkout_date=" + checkout_date +
                ", comments='" + comments + '\'' +
                '}';
    }
}
