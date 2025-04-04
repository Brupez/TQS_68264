package com.buser;

import com.buser.controller.TripController;
import com.buser.model.City;
import com.buser.model.Route;
import com.buser.model.Seat;
import com.buser.model.SeatType;
import com.buser.model.Trip;
import com.buser.service.CityService;
import com.buser.service.TripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TripController.class)
class TripControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TripService tripService;

    @MockBean
    CityService cityService;

    private City origin;
    private City destination;
    private Trip trip;

    @BeforeEach
    void setup() {
        origin = new City("Aveiro");
        destination = new City("Lisboa");
        Route route = new Route(origin, destination);
        Seat seat = new Seat("1A", SeatType.NORMAL, 20.0, route);
        trip = new Trip(route, LocalDateTime.now(), LocalDateTime.now().plusHours(5), List.of(seat));
    }

    @Test
    void testGetTrips() throws Exception {
        when(cityService.getCityById(1L)).thenReturn(origin);
        when(cityService.getCityById(2L)).thenReturn(destination);
        when(tripService.getTrips(eq(origin), eq(destination), any(LocalDateTime.class), any(LocalDateTime.class), eq("USD")))
                .thenReturn(List.of(trip));

        mockMvc.perform(get("/api/trips")
                        .param("originId", "1")
                        .param("destinationId", "2")
                        .param("startDate", "2022-01-01T10:00:00")
                        .param("endDate", "2022-01-02T10:00:00")
                        .param("currency", "USD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].route.origin.name").value("Aveiro"))
                .andExpect(jsonPath("$[0].route.destination.name").value("Lisboa"));
    }

    @Test
    void testGetTripById() throws Exception {
        when(tripService.getTripById(1L)).thenReturn(trip);

        mockMvc.perform(get("/api/trips/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.route.origin.name").value("Aveiro"))
                .andExpect(jsonPath("$.route.destination.name").value("Lisboa"));
    }

    // @Test
    // void testUpdateCurrentLocation() throws Exception {
    //     when(tripService.updateCurrentLocation(1L, destination)).thenReturn(trip);

    //     mockMvc.perform(patch("/api/trips/1/location")
    //                     .contentType(MediaType.APPLICATION_JSON)
    //                     .content("New Location"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.route.origin.name").value("Aveiro"));
    // }

    @Test
    void testGetUpcomingTripsByTerminal() throws Exception {
        when(tripService.getUpcomingTripsByOrigin("Aveiro")).thenReturn(List.of(trip));

        mockMvc.perform(get("/api/trips/terminal/Aveiro"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].route.origin.name").value("Aveiro"))
                .andExpect(jsonPath("$[0].route.destination.name").value("Lisboa"));
    }
}
