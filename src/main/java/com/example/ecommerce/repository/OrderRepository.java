package com.example.ecommerce.repository;

import com.example.ecommerce.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderDetails, Long> {
}
