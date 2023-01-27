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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void addOpinion(OpinionDtoRequest dto){
        try {
            Long id = Long.parseLong(dto.prodId());
            Product product = getProduct(id);
            Opinion opinion = opinionDtoToEntity.convert(dto);
            product.addOpinion(opinion);
            productRepository.save(product);
        }
        catch (NumberFormatException e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "invalid product ID");
        }
    }

    public List<OpinionDtoResponse> getOpinionsForProduct(Long productId){

        Product product = getProduct(productId);
        List<OpinionDtoResponse> opinions = product.getOpinions()
                .stream()
                .map(opinion -> {
                    return new OpinionDtoResponse(opinion.getText(), opinion.getMark());
                })
                .collect(Collectors.toList());

        return opinions;
    }

    Product getProduct(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
    }
}
