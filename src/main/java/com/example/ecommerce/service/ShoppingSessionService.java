package com.example.ecommerce.service;

import com.example.ecommerce.dto.shopping.PutItemIntoBoxDto;
import com.example.ecommerce.dto.shopping.RemoveItemFromBoxDto;
import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.ShoppingSession;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.ShoppingSessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ShoppingSessionService {

    private final ShoppingSessionRepository sessionRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    public ShoppingSessionService(ShoppingSessionRepository sessionRepository, CartItemRepository cartItemRepository, ProductService productService) {
        this.sessionRepository = sessionRepository;
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }

    @Transactional
    public void addProduct(PutItemIntoBoxDto dto) {
        ShoppingSession session = getSession(dto.userid());
        Product product = productService.getProduct(dto.productid());
        CartItem item = session.addProduct(product, dto.quantity());
        cartItemRepository.save(item);
        sessionRepository.save(session);
    }

    @Transactional
    public void removeProduct(RemoveItemFromBoxDto dto){
        ShoppingSession session = getSession(dto.userid());
        Product product = productService.getProduct(dto.productid());
        CartItem item = session.getItem(product);
        session.removeItem(item);
        cartItemRepository.delete(item);
        sessionRepository.save(session);
    }

    private ShoppingSession getSession(String userId){
        try {
            Optional<ShoppingSession> session = sessionRepository.findByUserId(Long.parseLong(userId));
            if (session.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "invalid user ID");
            }
            return session.get();
        }
        catch (NumberFormatException e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "invalid product ID");
        }
    }
}
