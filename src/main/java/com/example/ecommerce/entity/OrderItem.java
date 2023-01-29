package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name="order_items")
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name="product_id", referencedColumnName = "id")
    Product product;

    Integer quantity;
    Date created;

    @ManyToOne
    @JoinColumn(name="order_id", updatable = true, insertable = true)
    OrderDetails orderDetails;
}
