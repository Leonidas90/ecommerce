package com.example.ecommerce.dto.product;

import com.example.ecommerce.dto.discount.DiscountDto;

public record ProductDTO(Long id,
                         String name,
                         String description,
                         Double price,
                         DiscountDto discount,
                         Integer quantity)
{
}
