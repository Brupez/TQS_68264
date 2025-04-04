package com.buser.service;

import java.util.List;
import java.util.UUID;

import com.buser.model.Reservation;

public interface ReservationService {

    Reservation createReservation(String passengerName, String documentNumber, String email, String phone, Long tripId, List<Long> seatIds);//Ainda vai ser preciso alterar esse método
    void cancelReservation(Long reservationId);
    void changeReservation(Long reservationId, List<Long> newSeatIds);
    Reservation getReservationByToken(UUID token);
    Reservation getReservationById(Long id);
    List<Reservation> getReservationsByTripId(Long tripId);

} 
