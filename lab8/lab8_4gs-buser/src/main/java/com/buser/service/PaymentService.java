package com.buser.service;

import java.util.UUID;

import com.buser.model.Payment;

public interface PaymentService {
    Payment createPayment(Payment payment);
    Payment getPaymentByTransactionId(UUID transactionId);
}
