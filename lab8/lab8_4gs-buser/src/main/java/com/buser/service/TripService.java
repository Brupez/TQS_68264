package com.buser.service;

import java.time.LocalDateTime;
import java.util.List;

import com.buser.model.City;
import com.buser.model.Trip;

public interface TripService {
    List<Trip> getTripsBetweenCities(String originName, String destinationName);
    Trip getTripById(Long id);
    Trip updateCurrentLocation(Long tripId, City currentLocation);
    List<Trip> getUpcomingTripsByOrigin(String originName);
    List<Trip> getTrips(City origin, City destination, LocalDateTime startDateTime, LocalDateTime endDateTime,
            String currency);
    City getCityById(Long originId);
    
}
