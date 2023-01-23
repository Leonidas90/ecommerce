package com.example.ecommerce.converter;

import com.example.ecommerce.dto.user.SignUpDto;
import com.example.ecommerce.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SignUpDtoToEntityConverter implements Converter<SignUpDto, User> {
    @Override
    public User convert(SignUpDto dto) {
        final var entity = new User();
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        entity.setPassword(dto.password());
        entity.setAddress(dto.address());
        entity.setEmail(dto.email());
        return entity;

    }
}
