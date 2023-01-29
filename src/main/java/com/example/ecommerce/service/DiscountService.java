package com.example.ecommerce.service;

import com.example.ecommerce.converter.DiscountDtoToEntity;
import com.example.ecommerce.dto.discount.DiscountDto;
import com.example.ecommerce.entity.Discount;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.DiscountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final ProductService productService;
    private final DiscountDtoToEntity discountDtoToEntity;

    public DiscountService(DiscountRepository discountRepository, ProductService productService, DiscountDtoToEntity discountDtoToEntity) {
        this.discountRepository = discountRepository;
        this.productService = productService;
        this.discountDtoToEntity = discountDtoToEntity;
    }

    public void addToProduct(DiscountDto dto){
        Product product = productService.getProduct(dto.id());
        Discount discount = discountDtoToEntity.convert(dto);
        discount.addProduct(product);
        discountRepository.save(discount);
    }

    public void addToCategory(DiscountDto dto){
        List<Product> products = productService.getProductsFromCategory(dto.id());
        Discount discount = discountDtoToEntity.convert(dto);
        products.stream().forEach(obj -> discount.addProduct(obj));
        discountRepository.save(discount);
    }
}
