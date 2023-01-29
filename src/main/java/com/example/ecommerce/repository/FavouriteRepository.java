package com.example.ecommerce.repository;

import com.example.ecommerce.entity.FavouriteItem;
import com.example.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<FavouriteItem, Long> {
    public List<Product> findByUserId(Long id);
}
