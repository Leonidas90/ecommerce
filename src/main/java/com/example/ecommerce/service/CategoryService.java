package com.example.ecommerce.service;

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
    private final CategoryEntityToDtoConverter entityToDtoConverter;

    public CategoryService(CategoryRepository categoryRepository, ProductDtoToEntity productDtoToEntity, ProductEntitytoDto productEntitytoDto, CategoryEntityToDtoConverter entityToDtoConverter) {
        this.categoryRepository = categoryRepository;
        this.productDtoToEntity = productDtoToEntity;
        this.productEntitytoDto = productEntitytoDto;
        this.entityToDtoConverter = entityToDtoConverter;
    }

    public CategoryCreateResponseDto create(String name){
        if (categoryRepository.findByName(name).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "category already exist");
        }

        Category category = new Category();
        category.setName(name);
        Category entity = categoryRepository.save(category);
        return new CategoryCreateResponseDto(entity.getId().toString());
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

    public List<ProductDTO> getProductsFromCategory(String categoryId){
        Category category = getCategory(categoryId);
        List<ProductDTO> products = category.getProducts().stream()
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

    public Category getCategory(String categoryId){
        try {
            Long id = Long.parseLong(categoryId);
            return categoryRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
        }
        catch (NumberFormatException e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid product id");
        }
    }
}
