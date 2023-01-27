package com.example.ecommerce.controller;

import com.example.ecommerce.dto.shopping.ItemFromBox;
import com.example.ecommerce.dto.shopping.PutItemIntoBoxDto;
import com.example.ecommerce.dto.shopping.RemoveItemFromBoxDto;
import com.example.ecommerce.service.ShoppingSessionService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/box")
public class ShoppingController {

    private final ShoppingSessionService service;

    public ShoppingController(ShoppingSessionService service) {
        this.service = service;
    }

    @PostMapping("addProduct")
    public void addProduct(@RequestBody PutItemIntoBoxDto dto){
        service.addProduct(dto);
    }

    @PostMapping("removeProduct")
    public void addProduct(@RequestBody RemoveItemFromBoxDto dto){
        service.removeProduct(dto);
    }

    @GetMapping("getbox")
    public List<ItemFromBox> getBox(@RequestParam Long userId){
        return new ArrayList<>();
    }
}
