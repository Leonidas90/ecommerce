package com.example.ecommerce.dto.product;

import com.example.ecommerce.dto.discount.DiscountDto;
import java.util.List;

public record ProductFromCategoryDTO(String name,
                                     String description,
                                     Double price,
                                     DiscountDto discount,
                                     Integer quantity,
                                     List<String> opinions)
{
}
