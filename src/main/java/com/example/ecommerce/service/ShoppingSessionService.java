package com.example.ecommerce.service;

import com.example.ecommerce.dto.shopping.*;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.ShoppingSession;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.ShoppingSessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingSessionService {

    private final ShoppingSessionRepository sessionRepository;
    private final UserService userService;
    private final ProductService productService;

    public ShoppingSessionService(ShoppingSessionRepository sessionRepository, UserService userService, ProductService productService) {
        this.sessionRepository = sessionRepository;
        this.userService = userService;
        this.productService = productService;
    }

    @Transactional
    public void addProduct(PutItemIntoBoxDto dto) {
        ShoppingSession session = getSession(dto.basketid());
        Product product = productService.getProduct(dto.productid());
        session.addProduct(product, dto.quantity());
        sessionRepository.save(session);
    }

    @Transactional
    public void removeProduct(RemoveItemFromBoxDto dto){
        ShoppingSession session = getSession(dto.basketid());
        Product product = productService.getProduct(dto.productid());
        session.removeItem(product);
        sessionRepository.save(session);
    }

    public InitBasketResponse initBasket(InitBasketDto dto){
        ShoppingSession session = new ShoppingSession();
        Product product = productService.getProduct(dto.productid());
        session.addProduct(product, dto.quantity());
        ShoppingSession entity = sessionRepository.save(session);
        return new InitBasketResponse(entity.getId().toString());
    }

    public List<ItemFromBox> getProductsFromBasket(String basketid){
        ShoppingSession session = getSession(basketid);
        List<ItemFromBox> products = session.getItems().stream().map((item) -> {
            return new ItemFromBox(
                    item.getProduct().getName(),
                    item.getProduct().getPrice(),
                    item.getQuantity());
        }).collect(Collectors.toList());
        return products;
    }

    private ShoppingSession getSession(String basketId){
        try {
            ShoppingSession session = sessionRepository.
                    findById(Long.parseLong(basketId))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "invalid product ID"));

            return session;
        }
        catch (NumberFormatException e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "invalid basket ID");
        }
    }
}
