package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="payments")
public class Payment {
    @Id
    @GeneratedValue
    private Long id;
    private String type;
    private String account_number;

    @ManyToOne
    @JoinColumn(name="userid", updatable = true, insertable = true)
    private User user;
}
