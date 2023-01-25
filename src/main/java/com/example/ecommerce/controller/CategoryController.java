package com.example.ecommerce.controller;

import com.example.ecommerce.dto.category.CategoryCreateResponseDto;
import com.example.ecommerce.dto.category.CategoryDto;
import com.example.ecommerce.dto.category.CategoryListResponseDto;
import com.example.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService service;

    @GetMapping("list")
    CategoryListResponseDto list(){
        return service.getAll();
    }

    @PostMapping("create")
    CategoryCreateResponseDto create(@RequestBody
                    CategoryDto dto){
        System.out.println("NAME " + dto.name() + " " + dto.description());
        return service.create(dto);
    }
}
