package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="addresses")
public class Address {
    @Id
    @GeneratedValue
    private Long id;
    private String address;
    private String city;
    private String country;
    private String telephone;

    @ManyToOne
    @JoinColumn(name="userid", updatable = true, insertable = true)
    private User user;
}
