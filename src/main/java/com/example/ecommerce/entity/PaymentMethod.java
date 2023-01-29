package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

@Data
@Entity
@Table(name="payments")
public class PaymentMethod {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name="shipmentId", updatable = true, insertable = true)
    private ShipmentMethod shipment;
}
