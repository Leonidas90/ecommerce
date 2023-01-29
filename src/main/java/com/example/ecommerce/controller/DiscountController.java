package com.example.ecommerce.controller;

import com.example.ecommerce.dto.discount.DiscountDto;
import com.example.ecommerce.service.DiscountService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/discount")
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping("product")
    public void add(@RequestBody DiscountDto dto){
        discountService.addToProduct(dto);
    }

    @PostMapping("category")
    public void add2(@RequestBody DiscountDto dto){
        discountService.addToCategory(dto);
    }
}
