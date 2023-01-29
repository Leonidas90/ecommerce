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

    private final CategoryService categoryService;

    public ProductService(OpinionDtoToEntity opinionDtoToEntity, ProductDtoToEntity productDtoToEntity, ProductRepository productRepository, CategoryService categoryService) {
        this.opinionDtoToEntity = opinionDtoToEntity;
        this.productDtoToEntity = productDtoToEntity;
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public List<ProductDTO> getFromCategory(String category){
        List<ProductDTO> products = new ArrayList<>();
        return products;
    }

    public void addProduct(AddProductDto dto){
        Category category = categoryService.getCategory(dto.category());
        Product product = productDtoToEntity.convert(dto);
        product.setCategory(category);
        productRepository.save(product);
    }

    public void addOpinion(OpinionDtoRequest dto){
        try {
            Product product = getProduct(dto.productid());
            Opinion opinion = opinionDtoToEntity.convert(dto);
            product.addOpinion(opinion);
            productRepository.save(product);
        }
        catch (NumberFormatException e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "invalid product ID");
        }
    }

    public List<OpinionDtoResponse> getOpinionsForProduct(String productId){

        Product product = getProduct(productId);
        List<OpinionDtoResponse> opinions = product.getOpinions()
                .stream()
                .map(opinion -> {
                    return new OpinionDtoResponse(opinion.getText(), opinion.getMark());
                })
                .collect(Collectors.toList());

        return opinions;
    }

    public Product getProduct(String productId){
        try {
            Long id = Long.parseLong(productId);
            return productRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
        }
        catch (NumberFormatException e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid product id");
        }
    }

    public List<Product> getProductsFromCategory(String categoryId){
        List<Product> products = productRepository.findByCategory(categoryService.getCategory(categoryId));
        return products;
    }
}
