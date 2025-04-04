package com.buser.repository;

import com.buser.model.Seat;
import com.buser.model.SeatType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByTripIdAndType(Long tripId, SeatType type);
}
