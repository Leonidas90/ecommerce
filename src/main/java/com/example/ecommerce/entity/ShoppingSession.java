package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Entity
@Table(name="shopping_sessions")
public class ShoppingSession {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name="userId", referencedColumnName = "id", updatable = false, insertable = true)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="session", orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    public void addProduct(Product product, Integer quantity){
        if (isProductPresent(product)){
            CartItem item = getItem(product);
            item.setQuantity(quantity + item.getQuantity());
        }
        else{
            CartItem item = new CartItem();
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setSession(this);
            items.add(item);
        }
    }

    public void removeItem(Product product){
        items.remove(getItem(product));
    }

    private CartItem getItem(Product product){
        Optional<CartItem> item = items.stream().filter(obj -> obj.getProduct().getId() == product.getId()).findFirst();
        if (item.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found");
        }
        return item.get();
    }

    private boolean isProductPresent(Product product){
        return items.stream().filter(obj -> obj.getProduct().getId() == product.getId()).findFirst().isPresent();
    }

}
