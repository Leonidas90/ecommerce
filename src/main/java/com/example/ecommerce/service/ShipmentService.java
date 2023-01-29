package com.example.ecommerce.service;

import com.example.ecommerce.dto.shipment.PaymentDto;
import com.example.ecommerce.dto.shipment.ShipmentDto;
import com.example.ecommerce.entity.PaymentMethod;
import com.example.ecommerce.entity.ShipmentMethod;
import com.example.ecommerce.repository.ShipmentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    @PostConstruct
    private void buildDefault(){
        List<ShipmentMethod> shipments = shipmentRepository.findAll();
        if (!shipments.isEmpty()){
            return;
        }

        ShipmentMethod courier = new ShipmentMethod();
        courier.setName("Courier");
        courier.getPaymentMethods().add(addPaymentToShipment("Paypal", courier));

        ShipmentMethod personal = new ShipmentMethod();
        personal.setName("Personal");
        personal.getPaymentMethods().add(addPaymentToShipment("Cash", personal));
        personal.getPaymentMethods().add(addPaymentToShipment("Credit card", personal));

        shipmentRepository.saveAll(List.of(courier, personal));
    }

    private PaymentMethod addPaymentToShipment(String name, ShipmentMethod shipmentMethod){
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setName(name);
        paymentMethod.setShipment(shipmentMethod);
        return paymentMethod;
    }


    public ShipmentService(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public Map<String, List<PaymentDto>> getShipments(){
        List<ShipmentMethod> shipments = shipmentRepository.findAll();
        Map<String, List<PaymentDto>> payments = new HashMap<>();
        for (ShipmentMethod entity : shipments){
            String name = entity.getName();
            payments.put(name, entity.getPaymentMethods()
                    .stream()
                    .map(obj -> new PaymentDto(obj.getName()))
                    .collect(Collectors.toList()));
        }

        return payments;
    }
}
