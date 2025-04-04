package com.buser.repository;

import com.buser.model.Reservation;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation findByToken(UUID token);
    List<Reservation> findByTripId(Long tripId);
}
