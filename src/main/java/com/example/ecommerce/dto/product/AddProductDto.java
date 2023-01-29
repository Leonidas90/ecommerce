package com.example.ecommerce.dto.product;

public record AddProductDto(String name, String description, Double price, String categoryid, Integer quantity) {
}