package com.buser.controller;

import com.buser.dto.CreatePaymentRequest;
import com.buser.model.Payment;
import com.buser.model.PaymentType;
import com.buser.model.Reservation;
import com.buser.service.PaymentService;
import com.buser.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody CreatePaymentRequest request) {
        log.info("Creating payment for reservation ID: {}", request.getReservationId());
        try {
            Reservation reservation = reservationService.getReservationById(request.getReservationId());
            if (reservation == null) {
                log.warn("Reservation not found with ID: {}", request.getReservationId());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found");
            }
            Payment payment = new Payment();
            payment.setReservation(reservation);
            payment.setAmount(request.getAmount());
            try {
                payment.setPaymentType(PaymentType.valueOf(request.getPaymentType().toUpperCase()));  // Converte String para PaymentType
            } catch (IllegalArgumentException e) {
                log.error("Invalid payment type: {}", request.getPaymentType(), e);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid payment type");
            }
            payment.setPaymentData(request.getPaymentData());
            payment.setTransactionId(UUID.randomUUID());
            Payment createdPayment = paymentService.createPayment(payment);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPayment);
        } catch (ResponseStatusException e) {
            throw e; // Re-lança a exceção para preservar o status code
        } catch (RuntimeException e) {
            log.error("Error creating payment", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating payment");
        }
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<Payment> getPaymentByTransactionId(@PathVariable UUID transactionId) {
        log.info("Getting payment by transaction ID: {}", transactionId);
        try {
            Payment payment = paymentService.getPaymentByTransactionId(transactionId);
            if (payment != null) {
                return ResponseEntity.ok(payment);
            } else {
                log.warn("Payment not found with transaction ID: {}", transactionId);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found");
            }
        } catch (ResponseStatusException e) {
            throw e; // Re-lança a exceção para preservar o status code
        } catch (Exception e) {
            log.error("Error occurred while getting payment by transaction ID: {}", transactionId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while getting payment");
        }
    }
}
