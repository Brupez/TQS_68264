package com.buser;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.buser.controller.ReservationController;
import com.buser.dto.CreateReservationRequest;
import com.buser.model.Reservation;
import com.buser.model.Seat;
import com.buser.model.SeatType;
import com.buser.model.Trip;
import com.buser.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    private Reservation reservation;
    private Trip trip;
    private List<Seat> seats;
    private CreateReservationRequest request;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        trip = new Trip();
        seats = Arrays.asList(new Seat("1A", SeatType.NORMAL, 20.0, null));
        reservation = new Reservation("João Ferreira", "307374998", "jvbdf@ua.pt", "9123456789", LocalDateTime.now(), seats, trip);
        request = new CreateReservationRequest();
        request.setPassengerName("João Ferreira");
        request.setDocumentNumber("307374998");
        request.setEmail("jvbdf@ua.pt");
        request.setPhone("9123456789");
        request.setTripId(1L);
        request.setSeatIds(Arrays.asList(1L));
    }

    @Test
    public void testCreateReservation() throws Exception {
        when(reservationService.createReservation(anyString(), anyString(), anyString(), anyString(), anyLong(), anyList())).thenReturn(reservation);

        mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"passengerName\": \"João Ferreira\", \"documentNumber\": \"307374998\", \"email\": \"jvbdf@ua.pt\", \"phone\": \"9123456789\", \"tripId\": 1, \"seatIds\": [1] }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.passengerName").value("João Ferreira"))
                .andExpect(jsonPath("$.documentNumber").value("307374998"))
                .andExpect(jsonPath("$.email").value("jvbdf@ua.pt"))
                .andExpect(jsonPath("$.phone").value("9123456789"));
    }

    @Test
    public void testCancelReservation() throws Exception {
        doNothing().when(reservationService).cancelReservation(anyLong());

        mockMvc.perform(delete("/api/reservations/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testChangeReservation() throws Exception {
        doNothing().when(reservationService).changeReservation(anyLong(), anyList());

        mockMvc.perform(put("/api/reservations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[2]"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetReservationByToken() throws Exception {
        UUID token = reservation.getToken();
        when(reservationService.getReservationByToken(token)).thenReturn(reservation);

        mockMvc.perform(get("/api/reservations/token/" + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.passengerName").value("João Ferreira"))
                .andExpect(jsonPath("$.documentNumber").value("307374998"));
    }

    @Test
    public void testGetReservationsByTripId() throws Exception {
        when(reservationService.getReservationsByTripId(anyLong())).thenReturn(Arrays.asList(reservation));

        mockMvc.perform(get("/api/reservations/trip/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].passengerName").value("João Ferreira"))
                .andExpect(jsonPath("$[0].documentNumber").value("307374998"));
    }
}
