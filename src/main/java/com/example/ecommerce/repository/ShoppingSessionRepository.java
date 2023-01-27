package com.example.ecommerce.repository;

import com.example.ecommerce.entity.ShoppingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingSessionRepository extends JpaRepository<ShoppingSession, Long> {
    public Optional<ShoppingSession> findByUserId(Long id);
}
