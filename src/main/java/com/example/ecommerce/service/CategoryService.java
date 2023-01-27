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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void create(CategoryDto dto){
        Optional<Category> category = categoryRepository.findByName(dto.name());
        if (category.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "category already exist");
        }
        categoryRepository.save(dtoToEntityConverter.convert(dto));
    }

    public void addProduct(AddProductDto dto){
        Category category = getCategory(dto.category());
        if (checkIfProductExist(dto, category)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "product already exist");
        }

        Product product = productDtoToEntity.convert(dto);
        category.addProduct(product);
        categoryRepository.save(category);
    }

    public List<ProductDTO> getProductsFromCategory(String categoryName){
        Optional<Category> category = categoryRepository.findByName(categoryName);
        if (category.isEmpty()){
            return new ArrayList<>();
        }

        List<ProductDTO> products = category.get().getProducts().stream()
                .map((product -> productEntitytoDto.convert(product)))
                .collect(Collectors.toList());

        return products;
    }

    public CategoryListResponseDto getAll(){
        List<CategoryDto> result = new ArrayList<>();
        categoryRepository.findAll().stream().forEach((entity) -> result.add(entityToDtoConverter.convert(entity)));
        return new CategoryListResponseDto(result);
    }

    private boolean checkIfProductExist(AddProductDto dto, Category category){
        List<Product> products = category.getProducts();
        Product existed = products.stream()
                .filter(prod -> prod.getName().equals(dto.name()) && prod.getDescription().equals(dto.description()))
                .findAny()
                .orElse(null);

        return (existed != null);
    }

    private Category getCategory(String name){
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found"));
    }
}
