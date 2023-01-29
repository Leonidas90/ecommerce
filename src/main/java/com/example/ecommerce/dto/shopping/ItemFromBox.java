package com.example.ecommerce.dto.shopping;

import com.example.ecommerce.dto.discount.DiscountDto;
import com.example.ecommerce.entity.Discount;

public record ItemFromBox(String name, Double price, Integer units, DiscountDto discount) {
}
