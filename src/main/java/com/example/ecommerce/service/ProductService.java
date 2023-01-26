package com.example.ecommerce.service;

import com.example.ecommerce.converter.OpinionDtoToEntity;
import com.example.ecommerce.converter.ProductDtoToEntity;
import com.example.ecommerce.dto.opinion.OpinionDtoRequest;
import com.example.ecommerce.dto.opinion.OpinionDtoResponse;
import com.example.ecommerce.dto.product.AddProductDto;
import com.example.ecommerce.dto.product.ProductDTO;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Opinion;
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

    public List<ProductDTO> getFromCategory(String category){
        List<ProductDTO> products = new ArrayList<>();
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

    public String addOpinion(OpinionDtoRequest dto){
        System.out.println(dto.prodId());
        Optional<Product> productOptional = getProduct(Long.parseLong(dto.prodId()));
        if (productOptional.isPresent()){
            Product product = productOptional.get();
            Opinion opinion = opinionDtoToEntity.convert(dto);
            product.getOpinions().add(opinion);
            opinion.setProduct(product);
            productRepository.save(product);
            return "success";
        }
        else {
            return "fail";
        }
    }

    public List<OpinionDtoResponse> getOpinionsForProduct(Long productId){
        List<OpinionDtoResponse> opinions = new ArrayList<>();
        Optional<Product> product = getProduct(productId);
        if (product.isPresent()){
            product.get().getOpinions().stream().forEach((entity) -> {
                opinions.add(new OpinionDtoResponse(entity.getText(), entity.getMark()));
            });
        }

        return opinions;
    }

    Optional<Product> getProduct(Long productId){
        return productRepository.findById(productId);
    }
}
