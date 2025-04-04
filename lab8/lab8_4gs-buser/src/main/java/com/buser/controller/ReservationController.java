package com.buser.controller;

import com.buser.dto.CreateReservationRequest;
import com.buser.model.Reservation;
import com.buser.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody CreateReservationRequest request) {
        log.info("Creating reservation for passenger: {}, trip ID: {}", request.getPassengerName(), request.getTripId());
        try {
            Reservation reservation = reservationService.createReservation(
                request.getPassengerName(), request.getDocumentNumber(), request.getEmail(), request.getPhone(), request.getTripId(), request.getSeatIds());
            return new ResponseEntity<>(reservation, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            log.error("Error creating reservation", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating reservation");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        log.info("Cancelling reservation with ID: {}", id);
        try {
            reservationService.cancelReservation(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Error cancelling reservation with ID: {}", id, e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> changeReservation(@PathVariable Long id, @RequestBody List<Long> seatIds) {
        log.info("Changing reservation with ID: {}", id);
        try {
            reservationService.changeReservation(id, seatIds);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.error("Error changing reservation with ID: {}", id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error changing reservation");
        }
    }

    @GetMapping("/token/{token}")
    public ResponseEntity<Reservation> getReservationByToken(@PathVariable UUID token) {
        log.info("Getting reservation by token: {}", token);
        try {
            Reservation reservation = reservationService.getReservationByToken(token);
            if (reservation != null) {
                return ResponseEntity.ok(reservation);
            } else {
                log.warn("Reservation not found with token: {}", token);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found");
            }
        } catch (Exception e) {
            log.error("Error occurred while getting reservation by token: {}", token, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while getting reservation");
        }
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<List<Reservation>> getReservationsByTripId(@PathVariable Long tripId) {
        log.info("Getting reservations for trip ID: {}", tripId);
        try {
            List<Reservation> reservations = reservationService.getReservationsByTripId(tripId);
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            log.error("Error occurred while getting reservations for trip ID: {}", tripId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while getting reservations");
        }
    }
}
