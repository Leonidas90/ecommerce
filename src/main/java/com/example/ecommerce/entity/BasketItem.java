package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="basket_items")
public class BasketItem {
    @Id
    @GeneratedValue
    private Long id;
    private Integer quantity;

    @OneToOne
    @JoinColumn(name="product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name="session_id", updatable = true, insertable = true)
    private Basket session;
}
