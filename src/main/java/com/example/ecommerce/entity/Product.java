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
    private String name;
    private String description;
    private Double price;
    private Integer unitsInStock;

    @ManyToOne
    @JoinColumn(name="discountId", updatable = true, insertable = true)
    private Discount discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="categoryId", updatable = true, insertable = true)
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="product", orphanRemoval = true)
    private List<Opinion> opinions = new ArrayList<>();

    public void addOpinion(Opinion opinion){
        this.opinions.add(opinion);
        opinion.setProduct(this);
    }
}
