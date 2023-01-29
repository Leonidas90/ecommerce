package com.example.ecommerce.converter;

import com.example.ecommerce.dto.discount.DiscountDto;
import com.example.ecommerce.entity.Discount;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DiscountEntityToDto implements Converter<Discount, DiscountDto> {
    @Override
    public DiscountDto convert(Discount source) {
        if (source == null){
            return null;
        }
        return new DiscountDto(source.getId().toString(), source.getName(), source.getPercentage(), source.getActive());
    }
}
