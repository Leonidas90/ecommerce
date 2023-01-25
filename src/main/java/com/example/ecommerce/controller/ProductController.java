package com.example.ecommerce.controller;

import com.example.ecommerce.dto.product.AddProductDto;
import com.example.ecommerce.dto.product.ProductFromCategoryDTO;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("{category}")
    public List<ProductFromCategoryDTO> getFromCategory(@RequestParam String category){
        List<ProductFromCategoryDTO> pr = new ArrayList<>();
        return pr;
    }

    @PostMapping("add")
    public void add(@RequestBody AddProductDto dto){
        service.addProduct(dto);
    }
}
