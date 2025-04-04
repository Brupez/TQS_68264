package com.buser.controller;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.buser.model.City;
import com.buser.model.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.buser.service.CityService;
import com.buser.service.TripService;


@RestController
@RequestMapping("/api/trips")
public class TripController {

    private static final Logger log = LoggerFactory.getLogger(TripController.class);

    @Autowired
    private TripService tripService;

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<List<Trip>> getTrips(@RequestParam Long originId, @RequestParam Long destinationId, @RequestParam String startDate, @RequestParam String endDate, @RequestParam String currency) {
        log.info("Getting trips from origin city {} to destination city {} from {} to {} in {}", originId, destinationId, startDate, endDate, currency);

        try {
            City origin = cityService.getCityById(originId);
            City destination = cityService.getCityById(destinationId);
            LocalDateTime startDateTime = LocalDateTime.parse(startDate);
            LocalDateTime endDateTime = LocalDateTime.parse(endDate);

            List<Trip> trips = tripService.getTrips(origin, destination, startDateTime, endDateTime, currency);
            if (trips.isEmpty()) {
                log.warn("No trips found from {} to {} between {} and {}", origin.getName(), destination.getName(), startDate, endDate);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(trips);
            }
            return ResponseEntity.ok(trips);
        } catch (Exception e) {
            log.error("Error occurred while getting trips", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while getting trips");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        log.info("Getting trip with ID: {}", id);
        try {
            Trip trip = tripService.getTripById(id);
            if (trip != null) {
                return ResponseEntity.ok(trip);
            } else {
                log.warn("Trip not found with ID: {}", id);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found");
            }
        } catch (Exception e) {
            log.error("Error occurred while getting trip with ID: {}", id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while getting trip");
        }
    }

    @PatchMapping("/{id}/location")
    public ResponseEntity<Trip> updateCurrentLocation(@PathVariable Long id, @RequestBody City newLocation) {
        log.info("Updating current location of trip ID: {} to {}", id, newLocation);
        try {
            Trip updatedTrip = tripService.updateCurrentLocation(id, newLocation);
            return ResponseEntity.ok(updatedTrip);
        } catch (RuntimeException e) {
            log.error("Error updating current location of trip ID: {}", id, e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found");
        }
    }

    @GetMapping("/terminal/{originName}")
    public ResponseEntity<List<Trip>> getUpcomingTripsByTerminal(@PathVariable String originName) {
        log.info("Getting upcoming trips from terminal: {}", originName);
        try {
            List<Trip> trips = tripService.getUpcomingTripsByOrigin(originName);
            return ResponseEntity.ok(trips);
        } catch (Exception e) {
            log.error("Error occurred while getting upcoming trips from terminal: {}", originName, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while getting upcoming trips");
        }
    }
}
