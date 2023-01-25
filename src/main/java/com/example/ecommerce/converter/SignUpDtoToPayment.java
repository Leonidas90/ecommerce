package com.example.ecommerce.converter;

import com.example.ecommerce.dto.user.SignUpDto;
import com.example.ecommerce.entity.Payment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SignUpDtoToPayment implements Converter<SignUpDto, Payment> {
    @Override
    public Payment convert(SignUpDto dto) {
        Payment payment = new Payment();
        payment.setAccountNumber(dto.payment().accountNumber());
        payment.setType(dto.payment().type());
        return payment;
    }
}
