package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue
    private Long Id;
    String name;
    String description;
    Double price;

    @ManyToOne
    @JoinColumn(name="discountId", updatable = true, insertable = true)
    private Discount discount;

    @OneToOne
    @JoinColumn(name="categoryId", updatable = true, insertable = true)
    private Category category;

    @OneToOne
    @JoinColumn(name="inventoryId", updatable = true, insertable = true)
    private Inventory inventory;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="productId")
    private List<Opinion> opinions = new ArrayList<>();
}
