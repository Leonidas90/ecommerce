package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByTypeAndAccountNumber(String type, String number);
}
