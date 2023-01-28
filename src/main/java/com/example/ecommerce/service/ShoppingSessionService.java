package com.example.ecommerce.service;

import com.example.ecommerce.dto.shopping.PutItemIntoBoxDto;
import com.example.ecommerce.dto.shopping.RemoveItemFromBoxDto;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.ShoppingSession;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.ShoppingSessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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
        ShoppingSession session = getSession(dto.userid());
        if (session.getUser() == null){
            User user = userService.getUser(dto.userid());
            session.setUser(user);
        }
        Product product = productService.getProduct(dto.productid());
        session.addProduct(product, dto.quantity());
        sessionRepository.save(session);
    }

    @Transactional
    public void removeProduct(RemoveItemFromBoxDto dto){
        ShoppingSession session = getSession(dto.userid());
        Product product = productService.getProduct(dto.productid());
        session.removeItem(product);
        sessionRepository.save(session);
    }

    private ShoppingSession getSession(String userId){
        try {
            Optional<ShoppingSession> session = sessionRepository.findByUserId(Long.parseLong(userId));
            if (session.isPresent()){
                return session.get();
            }
            else
            {
                ShoppingSession newSession = new ShoppingSession();
                return newSession;
            }
        }
        catch (NumberFormatException e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "invalid product ID");
        }
    }
}
