package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="category", orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product){
        this.products.add(product);
        product.setCategory(this);
    }
}
