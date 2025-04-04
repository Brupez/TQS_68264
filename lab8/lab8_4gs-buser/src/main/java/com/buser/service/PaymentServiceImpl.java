package com.buser.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.buser.model.Payment;
import com.buser.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment createPayment(Payment payment) {
        payment.setTransactionId(UUID.randomUUID());
        logger.debug("Creating payment with transaction ID: {}", payment.getTransactionId());
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentByTransactionId(UUID transactionId) {
        logger.debug("Getting payment by transaction ID: {}", transactionId);
        Payment payment = paymentRepository.findByTransactionId(transactionId);
        if (payment != null) {
            logger.info("Found payment with transaction ID: {}", transactionId);
        } else {
            logger.warn("Payment not found with transaction ID: {}", transactionId);
        }
        return payment;
    }
}
