package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="opinions")
public class Opinion {
    @Id
    @GeneratedValue
    private Long id;
    private String text;
    private Integer mark;

    @ManyToOne
    @JoinColumn(name="productId", updatable = true, insertable = true)
    private Product product;
}
