package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id", referencedColumnName = "id", updatable = true, insertable = true)
    private Address address;
}

