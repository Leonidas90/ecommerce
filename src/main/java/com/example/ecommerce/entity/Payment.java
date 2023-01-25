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
    private String accountNumber;

    @OneToOne(mappedBy = "payment")
    private User user;
}
