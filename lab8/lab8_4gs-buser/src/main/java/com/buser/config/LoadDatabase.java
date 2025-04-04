package com.buser.config;

import com.buser.model.*;
import com.buser.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(CityRepository cityRepository,
                                   RouteRepository routeRepository,
                                   TripRepository tripRepository,
                                   SeatRepository seatRepository,
                                   ReservationRepository reservationRepository,
                                   PaymentRepository paymentRepository) {
        return args -> {
            // Cidades
            City lisbon = new City("Lisboa");
            City porto = new City("Porto");
            City coimbra = new City("Coimbra");
            cityRepository.saveAll(Arrays.asList(lisbon, porto, coimbra));

            // Rotas
            Route route1 = new Route(lisbon, porto);
            Route route2 = new Route(porto, coimbra);
            routeRepository.saveAll(Arrays.asList(route1, route2));

            // Assentos
            Seat seat1 = new Seat("1A", SeatType.NORMAL, 10.0, route1);
            Seat seat2 = new Seat("1B", SeatType.NORMAL, 10.0, route1);
            Seat seat3 = new Seat("2A", SeatType.PREMIUM, 20.0, route2);
            Seat seat4 = new Seat("2B", SeatType.PREMIUM, 20.0, route2);
            seatRepository.saveAll(Arrays.asList(seat1, seat2, seat3, seat4));

            // Viagens
            Trip trip1 = new Trip(route1, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(3), Arrays.asList(seat1, seat2));
            trip1.setTotalSeats(2);
            trip1.setAvailableSeats(2);
            trip1.setNormalSeats(2);
            trip1.setPremiumSeats(0);
            Trip trip2 = new Trip(route2, LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(2).plusHours(3), Arrays.asList(seat3, seat4));
            trip2.setTotalSeats(2);
            trip2.setAvailableSeats(2);
            trip2.setNormalSeats(0);
            trip2.setPremiumSeats(2);
            tripRepository.saveAll(Arrays.asList(trip1, trip2));

            // Reservas
            Reservation reservation1 = new Reservation("João Silva", "123456789", "joao.silva@example.com", "912345678", LocalDateTime.now(), Arrays.asList(seat1,seat2), trip1);
            reservation1.setStatus(ReservationStatus.CONFIRMED);
            Reservation reservation2 = new Reservation("Maria Santos", "987654321", "maria.santos@example.com", "987654321", LocalDateTime.now(), Arrays.asList(seat3,seat4), trip2);
            reservation2.setStatus(ReservationStatus.PENDING);
            reservationRepository.saveAll(Arrays.asList(reservation1, reservation2));

            // Pagamentos
            Payment payment1 = new Payment(10.0, PaymentType.CREDIT_CARD, "Card Payment Data", reservation1);
            payment1.setPaymentStatus(PaymentStatus.COMPLETED);
            Payment payment2 = new Payment(20.0, PaymentType.PAYPAL, "Paypal Payment Data", reservation2);
            payment2.setPaymentStatus(PaymentStatus.PENDING);
            paymentRepository.saveAll(Arrays.asList(payment1, payment2));
        };
    }
}
