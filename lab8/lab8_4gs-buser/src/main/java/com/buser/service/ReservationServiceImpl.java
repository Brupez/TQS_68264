package com.buser.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.buser.model.Reservation;
import com.buser.model.Seat;
import com.buser.model.Trip;
import com.buser.repository.ReservationRepository;
import com.buser.repository.SeatRepository;
import com.buser.repository.TripRepository;


@Service
public class ReservationServiceImpl implements ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TripRepository tripRepository;

    @Override
    public Reservation createReservation(String passengerName, String documentNumber, String email, String phone, Long tripId, List<Long> seatIds) {
        logger.debug("Creating reservation for passenger: {}, document: {}, trip ID: {}", passengerName, documentNumber, tripId);
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new RuntimeException("Trip not found"));
        List<Seat> seats = seatRepository.findAllById(seatIds);

        if (!trip.hasAvailableSeats(seats.size())) {
            logger.error("Not enough available seats for trip ID: {}", tripId);
            throw new RuntimeException("Not enough available seats");
        }

        for (Seat seat : seats) {
            if (seat.isReserved()) {
                logger.error("Seat {} is already reserved", seat.getSeatNumber());
                throw new RuntimeException("Seat " + seat.getSeatNumber() + " is already reserved");
            }
        }

        trip.reserveSeats(seats.size());

        for (Seat seat : seats) {
            seat.setReserved(true);
            seatRepository.save(seat);
        }

        UUID token = UUID.randomUUID();
        Reservation reservation = new Reservation(passengerName, documentNumber, email, phone, LocalDateTime.now(), seats, trip);
        reservation.setTotalPrice(seats.stream().mapToDouble(Seat::getPrice).sum());

        tripRepository.save(trip);

        logger.info("Reservation created with token: {}", token);
        return reservationRepository.save(reservation);
    }
    

    @Override
    public void cancelReservation(Long reservationId) {
        logger.debug("Cancelling reservation ID: {}", reservationId);
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        for (Seat seat : reservation.getSeats()) {
            seat.setReserved(false);
            seatRepository.save(seat);
        }

        Trip trip = reservation.getTrip();
        trip.releaseSeats(reservation.getSeats().size());
        tripRepository.save(trip);

        reservationRepository.delete(reservation);
        logger.info("Reservation ID: {} cancelled", reservationId);
    }

    @Override
    public void changeReservation(Long reservationId, List<Long> newSeatIds) {
        logger.debug("Changing reservation ID: {}", reservationId);
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        Trip trip = reservation.getTrip();

        for (Seat seat : reservation.getSeats()) {
            seat.setReserved(false);
            seatRepository.save(seat);
        }

        trip.releaseSeats(reservation.getSeats().size());

        List<Seat> newSeats = seatRepository.findAllById(newSeatIds);

        if (!trip.hasAvailableSeats(newSeats.size())) {
            logger.error("Not enough available seats for trip ID: {}", trip.getId());
            throw new RuntimeException("Not enough available seats");
        }

        for (Seat newSeat : newSeats) {
            if (newSeat.isReserved()) {
                logger.error("Seat {} is already reserved", newSeat.getSeatNumber());
                throw new RuntimeException("Seat " + newSeat.getSeatNumber() + " is already reserved");
            }
        }

        trip.reserveSeats(newSeats.size());

        for (Seat newSeat : newSeats) {
            newSeat.setReserved(true);
            seatRepository.save(newSeat);
        }

        reservation.setSeats(newSeats);
        reservation.setTotalPrice(newSeats.stream().mapToDouble(Seat::getPrice).sum());
        reservationRepository.save(reservation);

        tripRepository.save(trip);
        logger.info("Reservation ID: {} changed", reservationId);
    }

    @Override
    public Reservation getReservationByToken(UUID token) {
        logger.debug("Getting reservation by token: {}", token);
        return reservationRepository.findByToken(token);
    }

    @Override
    public List<Reservation> getReservationsByTripId(Long tripId) {
        logger.debug("Getting reservations for trip ID: {}", tripId);
        return reservationRepository.findByTripId(tripId);
    }

    @Override
    public Reservation getReservationById(Long id) {
        logger.info("Fetching reservation with ID: {}", id);
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()) {
            return reservation.get();
        } else {
            logger.warn("Reservation not found with ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found");
        }
    }
}
