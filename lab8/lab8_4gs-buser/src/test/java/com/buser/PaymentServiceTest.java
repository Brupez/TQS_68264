package com.buser;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.buser.model.Payment;
import com.buser.model.PaymentType;
import com.buser.model.Reservation;
import com.buser.repository.PaymentRepository;
import com.buser.service.PaymentServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Payment payment;
    private Reservation reservation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        reservation = new Reservation();
        payment = new Payment(100.0, PaymentType.CREDIT_CARD, "1234-5678-9876-5432", reservation);
    }

    @Test
    public void testCreatePayment() {
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment createdPayment = paymentService.createPayment(payment);

        assertNotNull(createdPayment);
        assertEquals(payment.getAmount(), createdPayment.getAmount());
        assertEquals(payment.getPaymentType(), createdPayment.getPaymentType());
        assertNotNull(createdPayment.getTransactionId());
    }

    @Test
    public void testGetPaymentByTransactionId() {
        UUID transactionId = payment.getTransactionId();
        when(paymentRepository.findByTransactionId(transactionId)).thenReturn(payment);

        Payment foundPayment = paymentService.getPaymentByTransactionId(transactionId);

        assertNotNull(foundPayment);
        assertEquals(payment, foundPayment);
    }

    @Test
    public void testGetPaymentByTransactionId_NotFound() {
        UUID transactionId = UUID.randomUUID();
        when(paymentRepository.findByTransactionId(transactionId)).thenReturn(null);

        Payment foundPayment = paymentService.getPaymentByTransactionId(transactionId);

        assertNull(foundPayment);
    }
}
