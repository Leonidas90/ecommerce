package com.example.ecommerce.controller;

import com.example.ecommerce.dto.shipment.PaymentDto;
import com.example.ecommerce.dto.shipment.ShipmentDto;
import com.example.ecommerce.service.ShipmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shipment")
public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @GetMapping
    public Map<String, List<PaymentDto>> getMethods(){
        return shipmentService.getShipments();
    }

    @PostMapping
    public void addMethods(@RequestBody ShipmentDto dto){

    }
}
