package com.buser.repository;

import com.buser.model.Payment;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    public List<Payment> findByTransactionId(String transactionId);
    Payment findByTransactionId(UUID transactionId);
}
