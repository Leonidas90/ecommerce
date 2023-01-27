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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id", referencedColumnName = "id", updatable = true, insertable = true)
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="payment_id", referencedColumnName = "id", updatable = true, insertable = true)
    private Payment payment;

    public void initSession(){
        shoppingSession = new ShoppingSession();
        shoppingSession.setTotal((double) 0);
        shoppingSession.setUser(this);
    }

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private ShoppingSession shoppingSession;
}
