package com.example.ecommerce.service;

import com.example.ecommerce.converter.CategoryDtoToEntityConverter;
import com.example.ecommerce.converter.CategoryEntityToDtoConverter;
import com.example.ecommerce.converter.ProductDtoToEntity;
import com.example.ecommerce.converter.ProductEntitytoDto;
import com.example.ecommerce.dto.category.CategoryCreateResponseDto;
import com.example.ecommerce.dto.category.CategoryDto;
import com.example.ecommerce.dto.category.CategoryListResponseDto;
import com.example.ecommerce.dto.product.AddProductDto;
import com.example.ecommerce.dto.product.ProductDTO;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductDtoToEntity productDtoToEntity;
    private final ProductEntitytoDto productEntitytoDto;
    private final CategoryDtoToEntityConverter dtoToEntityConverter;
    private final CategoryEntityToDtoConverter entityToDtoConverter;

    public CategoryService(CategoryRepository categoryRepository, ProductDtoToEntity productDtoToEntity, ProductEntitytoDto productEntitytoDto, CategoryDtoToEntityConverter dtoToEntityConverter, CategoryEntityToDtoConverter entityToDtoConverter) {
        this.categoryRepository = categoryRepository;
        this.productDtoToEntity = productDtoToEntity;
        this.productEntitytoDto = productEntitytoDto;
        this.dtoToEntityConverter = dtoToEntityConverter;
        this.entityToDtoConverter = entityToDtoConverter;
    }

    public CategoryCreateResponseDto create(CategoryDto dto){
        Optional<Category> category = categoryRepository.findByName(dto.name());
        if (category.isPresent()){
            return new CategoryCreateResponseDto("category already exist");
        }
        categoryRepository.save(dtoToEntityConverter.convert(dto));
        return new CategoryCreateResponseDto("success");
    }

    public String addProduct(AddProductDto dto){
        Optional<Category> category = categoryRepository.findByName(dto.category());
        if (category.isEmpty()){
            return "Category " + dto.category() + " doesn't exist";
        }

        List<Product> products = category.get().getProducts();
        Product existed = products.stream()
                .filter(prod -> prod.getName().equals(dto.name()) && prod.getDescription().equals(dto.description()))
                .findAny()
                .orElse(null);

        if (existed != null){
            return "product already exist";
        }

        Product product = productDtoToEntity.convert(dto);
        product.setCategory(category.get());
        category.get().getProducts().add(product);
        categoryRepository.save(category.get());
        return "success";
    }

    public List<ProductDTO> getProductsFromCategory(String categoryName){
        List<ProductDTO> products = new ArrayList<>();
        Optional<Category> category = categoryRepository.findByName(categoryName);
        if (category.isEmpty()){
            return products;
        }

        category.get().getProducts().stream().forEach((product) -> {
            products.add(productEntitytoDto.convert(product));
        });

        return products;
    }

    public CategoryListResponseDto getAll(){
        List<CategoryDto> result = new ArrayList<>();
        categoryRepository.findAll().stream().forEach((entity) -> result.add(entityToDtoConverter.convert(entity)));
        return new CategoryListResponseDto(result);
    }
}
