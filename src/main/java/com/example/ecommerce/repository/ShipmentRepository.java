package com.example.ecommerce.repository;

import com.example.ecommerce.entity.ShipmentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<ShipmentMethod, Long> {
}
