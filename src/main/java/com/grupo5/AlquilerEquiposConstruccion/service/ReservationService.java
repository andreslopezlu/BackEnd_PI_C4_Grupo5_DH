package com.grupo5.AlquilerEquiposConstruccion.service;

import com.grupo5.AlquilerEquiposConstruccion.model.Reservation;
import java.util.List;
import java.util.Optional;

public interface ReservationService {
    List<Reservation> findByUserId(Integer userId);
    List<Reservation> findByProductId(Integer productId);
    List<Reservation> getAllReservation();
    Optional<Reservation> getReservationById(Integer id);
    Reservation saveReservation(Reservation reservation);
    Reservation updateReservation(Reservation reservation);
    void deleteReservationById(Integer reservation);

}
