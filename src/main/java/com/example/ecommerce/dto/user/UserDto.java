package com.example.ecommerce.dto.user;

import com.example.ecommerce.entity.Address;

public record UserDto(String email, String firstName, String lastName, String password, PaymentDto payment, AddressDto address) {
}
