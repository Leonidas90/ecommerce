package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="orders")
public class OrderDetails {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", updatable = true, insertable = true)
    User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="orderDetails", orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();
}
