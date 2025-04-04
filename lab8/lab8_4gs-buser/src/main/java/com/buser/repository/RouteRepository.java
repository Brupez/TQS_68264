package com.buser.repository;

import com.buser.model.Route;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findByOriginNameAndDestinationName(String originName, String destinationName);
}
