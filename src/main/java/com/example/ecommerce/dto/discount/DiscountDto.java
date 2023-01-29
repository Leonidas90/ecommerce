package com.example.ecommerce.dto.discount;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(example = "{\n" + "  \"id\": \"string\",\n" + "  \"name\": \"string\",\n" + "  \"percentage\": 0,\n" + "  \"active\": true\n" + "}")
public record DiscountDto(String id, String name, Double percentage, Boolean active) {
}
