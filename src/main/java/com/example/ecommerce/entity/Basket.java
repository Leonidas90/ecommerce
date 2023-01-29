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
@Table(name="baskets")
public class Basket {
    @Id
    @GeneratedValue
    private Long id;
    private double total;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId", updatable = true, insertable = true)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="session", orphanRemoval = true)
    private List<BasketItem> items = new ArrayList<>();

    public void addProduct(Product product, Integer quantity){
        if (isProductPresent(product)){
            BasketItem item = getItem(product);
            item.setQuantity(quantity + item.getQuantity());
        }
        else{
            BasketItem item = new BasketItem();
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setSession(this);
            items.add(item);
        }

        this.total = calculateTotal();
    }

    public void addUser(User user){
        this.user = user;
    }

    public void removeItem(Product product){
        items.remove(getItem(product));
    }

    private BasketItem getItem(Product product){
        Optional<BasketItem> item = items.stream().filter(obj -> obj.getProduct().getId() == product.getId()).findFirst();
        if (item.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found");
        }
        return item.get();
    }

    private boolean isProductPresent(Product product){
        return items.stream().filter(obj -> obj.getProduct().getId() == product.getId()).findFirst().isPresent();
    }

    private double calculateTotal(){
        Double sum = items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        return sum;
    }

}
