package com.buser;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.buser.model.City;
import com.buser.model.Route;
import com.buser.model.Seat;
import com.buser.model.SeatType;
import com.buser.model.Trip;
import com.buser.repository.CityRepository;
import com.buser.repository.TripRepository;
import com.buser.service.TripServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TripServiceUnitTest {

    @Mock
    private TripRepository tripRepository;

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private TripServiceImpl tripService;

    private City origin;
    private City destination;
    private City newLocation;
    private Route route;
    private Trip trip;
    private List<Seat> seats;
    private Seat seat;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        origin = new City("Aveiro");
        destination = new City("Lisboa");
        newLocation = new City("Novo Local");
        route = new Route(origin, destination);
        seats = new ArrayList<>();
        seat = new Seat("1A", SeatType.NORMAL, 20.0, route);
        seats.add(seat);
        trip = new Trip(route, LocalDateTime.now(), LocalDateTime.now().plusHours(5), seats);
    }

    @Test
    public void testGetTripsBetweenCities() {
        Mockito.when(tripRepository.findByRouteOriginNameAndRouteDestinationName("Aveiro", "Lisboa")).thenReturn(List.of(trip));

        List<Trip> trips = tripService.getTripsBetweenCities("Aveiro", "Lisboa");
        assertNotNull(trips);
        assertEquals(1, trips.size());
        assertEquals("Aveiro", trips.get(0).getRoute().getOrigin().getName());
        assertEquals("Lisboa", trips.get(0).getRoute().getDestination().getName());
    }

    @Test
    public void testGetTripById() {
        Mockito.when(tripRepository.findById(1L)).thenReturn(Optional.of(trip));

        Trip foundTrip = tripService.getTripById(1L);
        assertNotNull(foundTrip);
        assertEquals(trip, foundTrip);
    }

    @Test
    public void testUpdateCurrentLocation() {
        Mockito.when(tripRepository.findById(1L)).thenReturn(Optional.of(trip));
        Mockito.when(tripRepository.save(any(Trip.class))).thenReturn(trip);

        Trip updatedTrip = tripService.updateCurrentLocation(1L, newLocation);
        assertNotNull(updatedTrip);
        assertEquals(newLocation, updatedTrip.getCurrentLocation());
    }

    @Test
    public void testGetUpcomingTripsByOrigin() {
        Mockito.when(tripRepository.findByRouteOriginName("Aveiro")).thenReturn(List.of(trip));

        List<Trip> trips = tripService.getUpcomingTripsByOrigin("Aveiro");
        assertNotNull(trips);
        assertEquals(1, trips.size());
        assertEquals("Aveiro", trips.get(0).getRoute().getOrigin().getName());
    }

    @Test
    public void testGetCityById() {
        Mockito.when(cityRepository.findById(1L)).thenReturn(Optional.of(origin));

        City foundCity = tripService.getCityById(1L);
        assertNotNull(foundCity);
        assertEquals(origin, foundCity);
    }
}
