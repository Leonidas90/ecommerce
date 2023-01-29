package com.example.ecommerce.controller;

import com.example.ecommerce.dto.shopping.*;
import com.example.ecommerce.service.ShoppingSessionService;
import com.example.ecommerce.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/basket")
public class ShoppingController {
    private final ShoppingSessionService service;
    private final UserService userService;

    public ShoppingController(ShoppingSessionService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @PostMapping("addProduct")
    public void addProduct(@RequestBody PutItemIntoBoxDto dto){
        service.addProduct(dto);
    }

    @PostMapping("init")
    public InitBasketResponse initBasket(@RequestBody InitBasketDto dto){
        return service.initBasket(dto);
    }

    @DeleteMapping("removeProduct")
    public void addProduct(@RequestBody RemoveItemFromBoxDto dto){
        service.removeProduct(dto);
    }

    @GetMapping("get")
    public List<ItemFromBox> getBox(@RequestParam String basketid){
        return service.getProductsFromBasket(basketid);
    }
}
