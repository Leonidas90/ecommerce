package com.example.ecommerce.converter;

import com.example.ecommerce.dto.discount.DiscountDto;
import com.example.ecommerce.entity.Discount;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DiscountDtoToEntity implements Converter<DiscountDto, Discount> {
    @Override
    public Discount convert(DiscountDto source) {
        final var entity = new Discount();
        entity.setActive(source.active());
        entity.setName(source.name());
        entity.setPercentage(source.percentage());
        return entity;
    }
}
