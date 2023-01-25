package com.example.ecommerce.service;

import com.example.ecommerce.converter.OpinionDtoToEntity;
import com.example.ecommerce.converter.ProductDtoToEntity;
import com.example.ecommerce.dto.product.AddProductDto;
import com.example.ecommerce.dto.product.ProductFromCategoryDTO;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final OpinionDtoToEntity opinionDtoToEntity;
    private final ProductDtoToEntity productDtoToEntity;

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(OpinionDtoToEntity opinionDtoToEntity, ProductDtoToEntity productDtoToEntity, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.opinionDtoToEntity = opinionDtoToEntity;
        this.productDtoToEntity = productDtoToEntity;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductFromCategoryDTO> getFromCategory(String category){
        List<ProductFromCategoryDTO> products = new ArrayList<>();
        return products;
    }

    public void addProduct(AddProductDto dto){

        Optional<Category> category = categoryRepository.findByName(dto.category());
        Product product = productDtoToEntity.convert(dto);
        if (category.isEmpty()){
            Category newCategory = new Category();
            newCategory.setName(dto.category());
            product.setCategory(newCategory);
        }
        else{
            product.setCategory(category.get());
        }

        productRepository.save(product);
    }

    Optional<Product> getProduct(String name){
        return productRepository.findByName(name);
    }
}
