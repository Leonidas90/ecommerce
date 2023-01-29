package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="shipments")
public class ShipmentMethod {

    @Id
    @GeneratedValue
    private Long Id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="shipment", orphanRemoval = true)
    private List<PaymentMethod> paymentMethods = new ArrayList<>();
}
