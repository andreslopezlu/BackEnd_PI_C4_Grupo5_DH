package com.grupo5.AlquilerEquiposConstruccion.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.AlquilerEquiposConstruccion.dto.ReservationDTO;
import com.grupo5.AlquilerEquiposConstruccion.exceptions.NotFoundException;
import com.grupo5.AlquilerEquiposConstruccion.model.Reservation;
import com.grupo5.AlquilerEquiposConstruccion.repository.ReservationRepository;
import com.grupo5.AlquilerEquiposConstruccion.service.ReservationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final Logger logger = Logger.getLogger(CategoryServiceImpl.class);

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<ReservationDTO> findByUserId(Integer userId) {
        List<Reservation> reservations = reservationRepository.findByUserId(userId);
        return reservations.stream()
                .map(reservation -> mapper.convertValue(reservation, ReservationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> findByProductId(Integer productId) {
        List<Reservation> reservations = reservationRepository.findByProductId(productId);
        return reservations.stream()
                .map(reservation -> mapper.convertValue(reservation, ReservationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> getAllReservation() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(reservation -> mapper.convertValue(reservation, ReservationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ReservationDTO> getReservationById(Integer id) throws NotFoundException {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new NotFoundException("The " +
                "reservation with the id: " + id + " was not found."));;
        return Optional.ofNullable(mapper.convertValue(reservation, ReservationDTO.class));
    }

    @Override
    public ReservationDTO saveReservation(ReservationDTO reservationDTO) {
        Reservation reservation = mapper.convertValue(reservationDTO, Reservation.class);
        Reservation savedReservation = reservationRepository.save(reservation);
        logger.info("The category was created successfully.");
        return mapper.convertValue(savedReservation, ReservationDTO.class);
    }


    @Override
    public ReservationDTO updateReservation(ReservationDTO reservationDTO, Integer id) throws NotFoundException {
        Optional<ReservationDTO> existingReservation = getReservationById(id);
        if (existingReservation.isPresent()){
            existingReservation.get().setCheck_in_date(reservationDTO.getCheck_in_date());
            existingReservation.get().setCheckout_date(reservationDTO.getCheckout_date());
            existingReservation.get().setComments(reservationDTO.getComments());
            Reservation reservation = mapper.convertValue(reservationDTO, Reservation.class);
            reservationRepository.save(reservation);
            logger.info("The reservation was updated successfully.");
        }
        return mapper.convertValue(existingReservation, ReservationDTO.class);
    }


    @Override
    public void deleteReservationById(Integer id) {
        reservationRepository.deleteById(id);
    }
}

