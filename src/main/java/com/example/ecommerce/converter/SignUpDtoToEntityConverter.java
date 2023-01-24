package com.example.ecommerce.converter;

import com.example.ecommerce.dto.user.SignUpDto;
import com.example.ecommerce.entity.Address;
import com.example.ecommerce.entity.Payment;
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
        entity.setEmail(dto.email());

        Address address = new Address();
        address.setAddress(dto.address().address());
        address.setCountry(dto.address().country());
        address.setCity(dto.address().city());
        address.setTelephone(dto.address().telephone());
        entity.getAddresses().add(address);

        Payment payment = new Payment();
        payment.setAccount_number(dto.payment().accountNumber());
        payment.setType(dto.payment().type());
        entity.getPayments().add(payment);
        return entity;
    }
}
