package com.example.ecommerce.controller;

import com.example.ecommerce.dto.category.CategoryCreateResponseDto;
import com.example.ecommerce.dto.category.CategoryDto;
import com.example.ecommerce.dto.category.CategoryListResponseDto;
import com.example.ecommerce.dto.product.AddProductDto;
import com.example.ecommerce.dto.product.ProductDTO;
import com.example.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService service;

    @GetMapping("all")
    public CategoryListResponseDto get(){
        return service.getAll();
    }

    @PostMapping("create")
    public CategoryCreateResponseDto create(@RequestParam String name){
        return service.create(name);
    }

    @GetMapping("products")
    public List<ProductDTO> getFromCategory(@RequestParam String category){
        return service.getProductsFromCategory(category);
    }

    @PostMapping("addProduct")
    public void addProduct(@RequestBody AddProductDto dto){
        service.addProduct(dto);
    }
}
