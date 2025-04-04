package com.buser.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buser.model.City;
import com.buser.model.Trip;
import com.buser.repository.CityRepository;
import com.buser.repository.TripRepository;

@Service
public class TripServiceImpl implements TripService {

    private static final Logger logger = LoggerFactory.getLogger(TripServiceImpl.class);
    

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private  CityRepository cityRepository;
    

    @Override
    public List<Trip> getTripsBetweenCities(String originName, String destinationName) {
        logger.debug("Searching for trips between {} and {}", originName, destinationName);
        List<Trip> trips = tripRepository.findByRouteOriginNameAndRouteDestinationName(originName, destinationName);
        logger.info("Found {} trips between {} and {}", trips.size(), originName, destinationName);
        return trips;
    }

    @Override
    public Trip getTripById(Long id) {
        logger.debug("Getting trip with ID: {}", id);
        Trip trip = tripRepository.findById(id).orElse(null);
        if (trip != null) {
            logger.info("Found trip with ID: {}", id);
        } else {
            logger.warn("Trip not found with ID: {}", id);
        }
        return trip;
    }

    @Override
    public Trip updateCurrentLocation(Long tripId, City newLocation) {
        logger.debug("Updating current location of trip ID: {} to {}", tripId, newLocation);
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new RuntimeException("Trip not found"));
        trip.setCurrentLocation(newLocation);
        Trip updatedTrip = tripRepository.save(trip);
        logger.info("Updated current location of trip ID: {} to {}", tripId, newLocation);
        return updatedTrip;
    }

    @Override
    public List<Trip> getUpcomingTripsByOrigin(String originName) {
        logger.debug("Getting upcoming trips from terminal: {}", originName);
        List<Trip> trips = tripRepository.findByRouteOriginName(originName);
        logger.info("Found {} upcoming trips from terminal: {}", trips.size(), originName);
        return trips;
    }

    

    @Override
    public City getCityById(Long id){
        return cityRepository.findById(id).orElse(null);
    }

    @Override
    public List<Trip> getTrips(City origin, City destination, LocalDateTime startDateTime, LocalDateTime endDateTime,
            String currency) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTrips'");
    }

    

}
