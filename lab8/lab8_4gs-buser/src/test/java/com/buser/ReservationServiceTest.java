package com.buser;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.buser.model.City;
import com.buser.model.Route;
import com.buser.model.Seat;
import com.buser.model.SeatType;
import com.buser.model.Reservation;
import com.buser.model.Trip;
import com.buser.repository.ReservationRepository;
import com.buser.repository.SeatRepository;
import com.buser.repository.TripRepository;
import com.buser.service.ReservationServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private City origin;
    private City destination;
    private Route route;
    private Trip trip;
    private List<Seat> seats;
    private Reservation reservation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        origin = new City("Aveiro");
        destination = new City("Lisboa");
        route = new Route(origin, destination);
        seats = new ArrayList<>();
        Seat seat = new Seat("1A", SeatType.NORMAL, 20.0, route);
        seats.add(seat);
        trip = new Trip(route, LocalDateTime.now(), LocalDateTime.now().plusHours(5), seats);
        reservation = new Reservation("João Ferreira", "307374998", "jvbdf@ua.pt", "9123456789", LocalDateTime.now(), seats, trip);
    }

    @Test
    public void testCreateReservationWhenSeatIsAvailable() {
        Seat seat = new Seat("1A", SeatType.NORMAL, 20.0, route);
        seat.setReserved(false); // Garantir que o assento não está reservado
        List<Seat> availableSeats = List.of(seat);

        Mockito.when(tripRepository.findById(1L)).thenReturn(Optional.of(trip));
        Mockito.when(seatRepository.findAllById(List.of(1L))).thenReturn(availableSeats);
        Mockito.when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation newReservation = reservationService.createReservation("João Ferreira", "307374998", "jvbdf@ua.pt", "9123456789", 1L, List.of(1L));

        assertNotNull(newReservation);
        assertEquals("João Ferreira", newReservation.getPassengerName());
        assertEquals("307374998", newReservation.getDocumentNumber());
    }

    @Test
    public void testCreateReservationWhenSeatIsAlreadyReserved() {
        Seat seat = new Seat("1A", SeatType.NORMAL, 20.0, route);
        seat.setReserved(true); // Garantir que o assento já está reservado
        List<Seat> reservedSeats = List.of(seat);

        Mockito.when(tripRepository.findById(1L)).thenReturn(Optional.of(trip));
        Mockito.when(seatRepository.findAllById(List.of(1L))).thenReturn(reservedSeats);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservationService.createReservation("João Ferreira", "307374998", "jvbdf@ua.pt", "9123456789", 1L, List.of(1L));
        });

        String expectedMessage = "Seat 1A is already reserved";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCancelReservation() {
        Mockito.when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        Mockito.doNothing().when(reservationRepository).delete(reservation);
        
        reservationService.cancelReservation(1L);

        verify(reservationRepository, times(1)).delete(reservation);
        assertFalse(reservation.getSeats().get(0).isReserved());
    }

    @Test
    public void testChangeReservation() {
        Mockito.when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        Seat newSeat = new Seat("1B", SeatType.NORMAL, 20.0, route);
        newSeat.setReserved(false); // Garantir que o novo assento não está reservado
        Mockito.when(seatRepository.findAllById(List.of(2L))).thenReturn(List.of(newSeat));
        Mockito.when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        reservationService.changeReservation(1L, List.of(2L));

        assertEquals(1, reservation.getSeats().size());
        assertEquals("1B", reservation.getSeats().get(0).getSeatNumber());
    }

    @Test
    public void testGetReservationByToken() {
        UUID token = reservation.getToken();
        Mockito.when(reservationRepository.findByToken(token)).thenReturn(reservation);

        Reservation foundReservation = reservationService.getReservationByToken(token);

        assertNotNull(foundReservation);
        assertEquals(reservation, foundReservation);
    }

    @Test
    public void testGetReservationsByTripId() {
        Mockito.when(reservationRepository.findByTripId(1L)).thenReturn(List.of(reservation));

        List<Reservation> reservations = reservationService.getReservationsByTripId(1L);

        assertNotNull(reservations);
        assertEquals(1, reservations.size());
        assertEquals(reservation, reservations.get(0));
    }

    @Test
    public void testGetReservationById() {
        Mockito.when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        Reservation foundReservation = reservationService.getReservationById(1L);

        assertNotNull(foundReservation);
        assertEquals(reservation, foundReservation);
    }
}
