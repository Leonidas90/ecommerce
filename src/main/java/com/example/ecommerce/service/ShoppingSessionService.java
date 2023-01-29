package com.example.ecommerce.service;

import com.example.ecommerce.converter.DiscountEntityToDto;
import com.example.ecommerce.dto.shopping.*;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.Basket;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.BasketRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingSessionService {

    private final BasketRepository basketRepository;
    private final UserService userService;
    private final ProductService productService;
    private final DiscountEntityToDto discountEntityToDto;

    public ShoppingSessionService(BasketRepository basketRepository, UserService userService, ProductService productService, DiscountEntityToDto discountEntityToDto) {
        this.basketRepository = basketRepository;
        this.userService = userService;
        this.productService = productService;
        this.discountEntityToDto = discountEntityToDto;
    }

    public void attachBasketToUser(String basketId, String id){
        Basket basket = getBasket(basketId);
        if (basket.getUser() != null){
            return;
        }
        basketRepository.flush();
        User user = userService.getUser(id);
        basket.addUser(user);
        basketRepository.save(basket);
    }

    public void addProduct(PutItemIntoBoxDto dto) {
        Basket session = getBasket(dto.basketid());
        Product product = productService.getProduct(dto.productid());
        session.addProduct(product, dto.quantity());
        basketRepository.save(session);
    }

    public void removeProduct(RemoveItemFromBoxDto dto){
        Basket session = getBasket(dto.basketid());
        Product product = productService.getProduct(dto.productid());
        session.removeItem(product);
        basketRepository.save(session);
    }

    public InitBasketResponse initBasket(InitBasketDto dto){
        Basket basket = new Basket();
        Product product = productService.getProduct(dto.productid());
        basket.addProduct(product, dto.quantity());
        Basket entity = basketRepository.save(basket);
        return new InitBasketResponse(entity.getId().toString());
    }

    public List<ItemFromBox> getProductsFromBasket(String basketId){
        Basket session = getBasket(basketId);
        List<ItemFromBox> products = session.getItems().stream().map((item) -> new ItemFromBox(
                item.getProduct().getName(),
                item.getProduct().getPrice(),
                item.getQuantity(),
                discountEntityToDto.convert(item.getProduct().getDiscount()))).collect(Collectors.toList());
        return products;
    }

    private Basket getBasket(String basketId){
        try {
            Basket basket = basketRepository.
                    findById(Long.parseLong(basketId))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "invalid product ID"));

            return basket;
        }
        catch (NumberFormatException e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "invalid basket ID");
        }
    }
}
