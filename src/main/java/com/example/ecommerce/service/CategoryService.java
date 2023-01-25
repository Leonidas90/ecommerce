package com.example.ecommerce.service;

import com.example.ecommerce.converter.CategoryDtoToEntityConverter;
import com.example.ecommerce.converter.CategoryEntityToDtoConverter;
import com.example.ecommerce.dto.category.CategoryCreateResponseDto;
import com.example.ecommerce.dto.category.CategoryDto;
import com.example.ecommerce.dto.category.CategoryListResponseDto;
import com.example.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryDtoToEntityConverter dtoToEntityConverter;
    private final CategoryEntityToDtoConverter entityToDtoConverter;

    public CategoryService(CategoryRepository categoryRepository, CategoryDtoToEntityConverter dtoToEntityConverter, CategoryEntityToDtoConverter entityToDtoConverter) {
        this.categoryRepository = categoryRepository;
        this.dtoToEntityConverter = dtoToEntityConverter;
        this.entityToDtoConverter = entityToDtoConverter;
    }

    public CategoryCreateResponseDto create(CategoryDto dto){
        categoryRepository.save(dtoToEntityConverter.convert(dto));
        return new CategoryCreateResponseDto("success");
    }

    public CategoryListResponseDto getAll(){
        List<CategoryDto> result = new ArrayList<>();
        categoryRepository.findAll().stream().forEach((entity) -> result.add(entityToDtoConverter.convert(entity)));
        return new CategoryListResponseDto(result);
    }
}
