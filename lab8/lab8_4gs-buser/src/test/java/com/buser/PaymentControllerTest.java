package com.buser;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.buser.controller.PaymentController;
import com.buser.dto.CreatePaymentRequest;
import com.buser.model.Payment;
import com.buser.model.PaymentType;
import com.buser.model.Reservation;
import com.buser.service.PaymentService;
import com.buser.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private ReservationService reservationService;

    private Payment payment;
    private Reservation reservation;
    private CreatePaymentRequest request;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        reservation = new Reservation();
        reservation.setId(1L);
        
        payment = new Payment(100.0, PaymentType.CREDIT_CARD, "paymentData", reservation);
        
        request = new CreatePaymentRequest();
        request.setReservationId(1L);
        request.setAmount(100.0);
        request.setPaymentType("CREDIT_CARD");
        request.setPaymentData("paymentData");
    }

    @Test
    public void testCreatePayment() throws Exception {
        when(reservationService.getReservationById(1L)).thenReturn(reservation);
        when(paymentService.createPayment(any(Payment.class))).thenReturn(payment);

        mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"reservationId\": 1, \"amount\": 100.0, \"paymentType\": \"CREDIT_CARD\", \"paymentData\": \"paymentData\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.paymentType").value("CREDIT_CARD"))
                .andExpect(jsonPath("$.paymentData").value("paymentData"));
    }

    @Test
    public void testCreatePayment_InvalidPaymentType() throws Exception {
        mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"reservationId\": 1, \"amount\": 100.0, \"paymentType\": \"INVALID_TYPE\", \"paymentData\": \"paymentData\" }"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreatePayment_ReservationNotFound() throws Exception {
        when(reservationService.getReservationById(1L)).thenReturn(null);

        mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"reservationId\": 1, \"amount\": 100.0, \"paymentType\": \"CREDIT_CARD\", \"paymentData\": \"paymentData\" }"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetPaymentByTransactionId() throws Exception {
        UUID transactionId = payment.getTransactionId();
        when(paymentService.getPaymentByTransactionId(transactionId)).thenReturn(payment);

        mockMvc.perform(get("/api/payments/transaction/" + transactionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.paymentType").value("CREDIT_CARD"));
    }

    @Test
    public void testGetPaymentByTransactionId_NotFound() throws Exception {
        UUID transactionId = UUID.randomUUID();
        when(paymentService.getPaymentByTransactionId(transactionId)).thenReturn(null);

        mockMvc.perform(get("/api/payments/transaction/" + transactionId))
                .andExpect(status().isNotFound());
    }
}
