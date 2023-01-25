package com.example.ecommerce.dto.user;

public record UserDto(String email, String firstName, String lastName, String password, PaymentDto payment, AddressDto address) {
}
