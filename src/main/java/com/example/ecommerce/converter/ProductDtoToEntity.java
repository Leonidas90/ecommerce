package com.example.ecommerce.converter;

import com.example.ecommerce.dto.product.AddProductDto;
import com.example.ecommerce.entity.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoToEntity implements Converter<AddProductDto, Product>{
    @Override
    public Product convert(AddProductDto dto) {
        final var entity = new Product();
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setPrice(dto.price());
        entity.setUnitsInStock(dto.quantity());
        return entity;
    }
}
