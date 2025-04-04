package com.buser.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "trip")
public class Trip {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @Column(nullable = false)
    private LocalDateTime departureTime;

    @Column(nullable = false)
    private LocalDateTime arrivalTime;

    

    @Column(length = 100)

    private City currentLocation;

    @Column(length = 100)

    private String nextLocation;

    @Column(nullable = false)
    private int totalSeats;

    @Column(nullable = false)
   
    private int availableSeats;

    @Column(nullable = false)
 
    private int normalSeats;

    @Column(nullable = false)
  
    private int premiumSeats;
    

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    public Trip() {
    }

    public Trip(Route route, LocalDateTime departureTime, LocalDateTime arrivalTime, 
            List<Seat> seats) {
        this.route = route;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        
        this.currentLocation = route.getOrigin();
        this.seats = seats;
    }
    public boolean hasAvailableSeats(int numberOfSeats) {
        long reservedSeatsCount = seats.stream().filter(Seat::isReserved).count();
        return (seats.size() - reservedSeatsCount) >= numberOfSeats;
    }


    public void reserveSeats(int seatCount) {
        if (hasAvailableSeats(seatCount)) {
            this.availableSeats -= seatCount;
        } else {
            throw new RuntimeException("Not enough available seats");
        }
    }

    public void releaseSeats(int seatCount) {
        this.availableSeats += seatCount;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

   
    public City getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(City currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getNextLocation() {
        return nextLocation;
    }

    public void setNextLocation(String nextLocation) {
        this.nextLocation = nextLocation;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getNormalSeats() {
        return normalSeats;
    }

    public void setNormalSeats(int normalSeats) {
        this.normalSeats = normalSeats;
    }

    public int getPremiumSeats() {
        return premiumSeats;
    }

    public void setPremiumSeats(int premiumSeats) {
        this.premiumSeats = premiumSeats;
    }

    
    
    
   

    
}
