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
    Integer unitsInStock;

    @ManyToOne
    @JoinColumn(name="discountId", updatable = true, insertable = true)
    private Discount discount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="categoryId", updatable = true, insertable = true)
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="product")
    //@JoinColumn(name="productId")
    private List<Opinion> opinions = new ArrayList<>();
}
