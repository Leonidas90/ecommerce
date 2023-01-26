package com.example.ecommerce.converter;

import com.example.ecommerce.dto.product.ProductDTO;
import com.example.ecommerce.entity.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductEntitytoDto implements Converter<Product, ProductDTO> {
    private final DiscountEntityToDto converter;

    public ProductEntitytoDto(DiscountEntityToDto converter) {
        this.converter = converter;
    }

    @Override
    public ProductDTO convert(Product source) {
        return new ProductDTO(source.getId(), source.getName(), source.getDescription(), source.getPrice(), converter.convert(source.getDiscount()), source.getUnitsInStock());
    }
}
