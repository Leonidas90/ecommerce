package com.example.ecommerce.controller;

import com.example.ecommerce.dto.opinion.OpinionDtoRequest;
import com.example.ecommerce.dto.opinion.OpinionDtoResponse;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping("addOpinion")
    public void addOpinion(@RequestBody OpinionDtoRequest dto){
        service.addOpinion(dto);
    }

    @GetMapping("getOpinion")
    public List<OpinionDtoResponse> getOpinion(@RequestParam Long productId){
        return service.getOpinionsForProduct(productId);
    }
}
