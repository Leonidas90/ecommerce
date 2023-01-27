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
    private Double total;

    @OneToOne
    @JoinColumn(name="userId", referencedColumnName = "id", updatable = true, insertable = true)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="session", orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    public CartItem addProduct(Product product, Integer quantity){
        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setSession(this);
        items.add(item);
        this.total = calculateTotal();
        return item;
    }

    public void removeItem(CartItem item){
        items.remove(item);
    }

    public CartItem getItem(Product product){
        Optional<CartItem> item = items.stream().filter(obj -> obj.getId() == product.getId()).findFirst();
        if (item.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found");
        }
        return item.get();
    }

    private Double calculateTotal(){
        Double sum = items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        return sum;
    }
}
