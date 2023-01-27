package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="cart_items")
public class CartItem {
    @Id
    @GeneratedValue
    private Long id;
    private Integer quantity;

    @OneToOne
    @JoinColumn(name="product_id", referencedColumnName = "id", insertable=true, updatable=true)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="session_id", updatable = true, insertable = true)
    private ShoppingSession session;
}
