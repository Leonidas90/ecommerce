package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="discounts")
public class Discount {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Double percentage;
    private Boolean active;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="discountId")
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product){
        product.setDiscount(this);
        products.add(product);
    }
}
