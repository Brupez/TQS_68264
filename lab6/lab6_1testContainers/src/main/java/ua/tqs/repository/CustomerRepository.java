package ua.tqs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.tqs.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {}
