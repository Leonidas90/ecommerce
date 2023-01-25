package com.example.ecommerce.converter;

import com.example.ecommerce.dto.opinion.OpinionDto;
import com.example.ecommerce.entity.Opinion;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OpinionDtoToEntity implements Converter<OpinionDto, Opinion> {
    @Override
    public Opinion convert(OpinionDto dto) {
        final var entity = new Opinion();
        entity.setMark(dto.mark());
        entity.setText(dto.text());
        return entity;
    }
}
