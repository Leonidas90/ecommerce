package com.example.ecommerce.converter;

import com.example.ecommerce.dto.category.CategoryDto;
import com.example.ecommerce.entity.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoToEntityConverter implements Converter<CategoryDto, Category> {
    @Override
    public Category convert(CategoryDto dto) {
        final var entity = new Category();
        entity.setDescription(dto.description());
        entity.setName(dto.name());
        return entity;
    }
}
