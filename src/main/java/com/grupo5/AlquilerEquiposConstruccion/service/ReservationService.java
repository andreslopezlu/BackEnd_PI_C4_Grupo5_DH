package com.grupo5.AlquilerEquiposConstruccion.service;

import com.grupo5.AlquilerEquiposConstruccion.dto.ReservationDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Reservation;
import java.util.List;
import java.util.Optional;

public interface ReservationService {
    List<ReservationDTO> findByUserId(Integer userId);
    List<ReservationDTO> findByProductId(Integer productId);
    List<ReservationDTO> getAllReservation();
    Optional<ReservationDTO> getReservationById(Integer id) throws NotFoundException;
    ReservationDTO saveReservation(ReservationDTO reservationDTO);
    ReservationDTO updateReservation(ReservationDTO reservationDTO) throws NotFoundException;
    void deleteReservationById(Integer reservation);

}
