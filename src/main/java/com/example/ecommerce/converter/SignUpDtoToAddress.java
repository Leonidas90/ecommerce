package com.example.ecommerce.converter;

import com.example.ecommerce.dto.user.SignUpDto;
import com.example.ecommerce.entity.Address;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SignUpDtoToAddress implements Converter<SignUpDto, Address> {
    @Override
    public Address convert(SignUpDto dto) {
        Address address = new Address();
        address.setAddress(dto.address().address());
        address.setCountry(dto.address().country());
        address.setCity(dto.address().city());
        address.setTelephone(dto.address().telephone());
        return address;
    }
}
