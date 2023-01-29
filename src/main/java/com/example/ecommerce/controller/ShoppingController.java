package com.example.ecommerce.controller;

import com.example.ecommerce.dto.shopping.*;
import com.example.ecommerce.service.SessionService;
import com.example.ecommerce.service.ShoppingSessionService;
import com.example.ecommerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/basket")
public class ShoppingController {

    private static final String USER_ID_ATTRIBUTE = "userId";

    private final ShoppingSessionService service;
    private final UserService userService;
    private final SessionService sessionService;

    public ShoppingController(ShoppingSessionService service, UserService userService, SessionService sessionService) {
        this.service = service;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @PostMapping("addProduct")
    public void addProduct(@RequestBody PutItemIntoBoxDto dto, final HttpSession httpSession){
        service.addProduct(dto);
        if (sessionService.isUserLoggedIn(httpSession)){
            service.attachBasketToUser(dto.basketid(), httpSession.getAttribute(USER_ID_ATTRIBUTE).toString());
        }
    }

    @PostMapping("init")
    public InitBasketResponse initBasket(@RequestBody InitBasketDto dto, final HttpSession httpSession){
        InitBasketResponse response = service.initBasket(dto);
        if (sessionService.isUserLoggedIn(httpSession)){
            service.attachBasketToUser(response.basketid(), httpSession.getAttribute(USER_ID_ATTRIBUTE).toString());
        }
        return response;
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
