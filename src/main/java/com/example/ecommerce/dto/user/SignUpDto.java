package com.example.ecommerce.dto.user;

public record SignUpDto(String firstName,String lastName, String email, String password, AddressDto address, PaymentDto payment) {
}
