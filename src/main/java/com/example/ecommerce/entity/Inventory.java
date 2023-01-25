package com.example.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="inventory")
public class Inventory {
    @Id
    @GeneratedValue
    private Long Id;
    private Integer quantity;
}
