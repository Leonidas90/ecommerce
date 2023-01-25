package com.example.ecommerce.controller;

import com.example.ecommerce.dto.category.CategoryCreateResponseDto;
import com.example.ecommerce.dto.category.CategoryDto;
import com.example.ecommerce.dto.category.CategoryListResponseDto;
import com.example.ecommerce.service.CategoryService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
    CategoryCreateResponseDto create(
            @RequestBody(content =
            @Content(examples = {
            @ExampleObject(name = "Category example",
                    summary = "Category example",
                    value = "{\"name\": \"string\",\n" + "\"description\": \"string\"\n" + "}")}))
                    CategoryDto dto){
        return service.create(dto);
    }
}
