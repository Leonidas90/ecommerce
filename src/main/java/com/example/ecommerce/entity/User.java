package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String type = "normal";

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private ShoppingSession shoppingSession;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user", orphanRemoval = true)
    private List<OrderDetails> orders = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id", referencedColumnName = "id", updatable = true, insertable = true)
    private Address address;
}

