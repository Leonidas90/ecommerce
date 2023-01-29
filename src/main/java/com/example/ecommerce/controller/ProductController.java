package com.example.ecommerce.controller;

import com.example.ecommerce.dto.opinion.OpinionDtoRequest;
import com.example.ecommerce.dto.opinion.OpinionDtoResponse;
import com.example.ecommerce.dto.product.FavouriteProductDto;
import com.example.ecommerce.service.FavoriteProductService;
import com.example.ecommerce.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService service;
    private final FavoriteProductService favoriteProductService;

    public ProductController(ProductService service, FavoriteProductService favoriteProductService) {
        this.service = service;
        this.favoriteProductService = favoriteProductService;
    }

    @PostMapping("addOpinion")
    public void addOpinion(@RequestBody OpinionDtoRequest dto){
        service.addOpinion(dto);
    }

    @PostMapping("addFavourite")
    public void addFavourite(@RequestBody FavouriteProductDto dto){
        favoriteProductService.addFavourite(dto);
    }

    @DeleteMapping("removeFavourite")
    public void removeFavourite(@RequestBody FavouriteProductDto dto){
        favoriteProductService.removeFavourite(dto);
    }

    @GetMapping("getOpinion")
    public List<OpinionDtoResponse> getOpinion(@RequestParam String productid){
        return service.getOpinionsForProduct(productid);
    }
}
