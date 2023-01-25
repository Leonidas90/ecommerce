package com.example.ecommerce.service;

import com.example.ecommerce.entity.Address;
import com.example.ecommerce.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository repository;

    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    public Optional<Address> findAddress(String address, String city, String telephone){
        return repository.findByAddressAndCityAndTelephone(address, city, telephone);
    }
}
