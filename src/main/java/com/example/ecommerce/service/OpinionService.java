package com.example.ecommerce.service;

import com.example.ecommerce.converter.OpinionDtoToEntity;
import com.example.ecommerce.dto.opinion.OpinionDto;
import com.example.ecommerce.entity.Opinion;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.OpinionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OpinionService {

    private final OpinionRepository repository;
    private final OpinionDtoToEntity converter;
    private final ProductService productService;

    public OpinionService(OpinionRepository repository, OpinionDtoToEntity converter, ProductService productService) {
        this.repository = repository;
        this.converter = converter;
        this.productService = productService;
    }

    public String add(OpinionDto dto){
        Optional<Product> product = productService.getProduct(dto.productName());
        if (product.isEmpty()){
            return "failed";
        }

        Opinion opinion = converter.convert(dto);
        opinion.setProduct(product.get());
        repository.save(opinion);

        return "success";
    }
}
