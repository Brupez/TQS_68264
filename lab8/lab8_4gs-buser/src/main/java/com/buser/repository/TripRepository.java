package com.buser.repository;

import com.buser.model.Trip;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findByRouteOriginNameAndRouteDestinationName(String originName, String destinationName);
    List<Trip> findByRouteOriginName(String originName);
}
