package com.example.ecommerce.service;

import com.example.ecommerce.dto.product.FavouriteProductDto;
import com.example.ecommerce.entity.FavouriteItem;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.FavouriteRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class FavoriteProductService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final FavouriteRepository favouriteRepository;
    private final ProductService productService;
    private final UserService userService;

    public FavoriteProductService(UserRepository userRepository, ProductRepository productRepository, FavouriteRepository favouriteRepository, ProductService productService, UserService userService) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.favouriteRepository = favouriteRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public void addFavourite(FavouriteProductDto dto){
        Product product = productService.getProduct(dto.productid());
        User user = userService.getUser(dto.userid());
        FavouriteItem item = new FavouriteItem();
        item.setProduct(product);
        item.setUser(user);
        favouriteRepository.save(item);
    }

    public void removeFavourite(FavouriteProductDto dto){

    }
}
