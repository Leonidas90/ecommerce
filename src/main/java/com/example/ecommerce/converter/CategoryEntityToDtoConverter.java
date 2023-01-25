package com.example.ecommerce.converter;
import com.example.ecommerce.dto.category.CategoryDto;
import com.example.ecommerce.entity.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryEntityToDtoConverter implements Converter<Category, CategoryDto>{
    @Override
    public CategoryDto convert(Category source) {
        CategoryDto dto = new CategoryDto(source.getName(), source.getDescription());
        return dto;
    }
}
