package com.example.ecommerce.service;

import com.example.ecommerce.converter.SignUpDtoToAddress;
import com.example.ecommerce.converter.SignUpDtoToEntityConverter;
import com.example.ecommerce.converter.SignUpDtoToPayment;
import com.example.ecommerce.dto.user.SignInDto;
import com.example.ecommerce.dto.user.SignInResponseDto;
import com.example.ecommerce.dto.user.SignUpDto;
import com.example.ecommerce.dto.user.SignUpResponseDto;
import com.example.ecommerce.entity.Address;
import com.example.ecommerce.entity.Payment;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    PaymentService paymentService;

    @Autowired
    AddressService addressService;

    private final UserRepository usersRepository;
    private final SignUpDtoToEntityConverter usersConverter;
    private final SignUpDtoToAddress addressConverter;
    private final SignUpDtoToPayment paymentConverter;

    public UserService(UserRepository usersRepository, SignUpDtoToEntityConverter usersConverter, SignUpDtoToAddress addressConverter, SignUpDtoToPayment paymentConverter) {
        this.usersRepository = usersRepository;
        this.usersConverter = usersConverter;
        this.addressConverter = addressConverter;
        this.paymentConverter = paymentConverter;
    }

    public SignInResponseDto signIn(SignInDto dto){
        Optional<User> users = usersRepository.findByEmail(dto.email());
        if (users.isEmpty()){
            return new SignInResponseDto("failed");
        }

        SignInResponseDto response;
        if (users.get().getPassword().equals(dto.password())){
            response = new SignInResponseDto("success");
        }
        else{
            response = new SignInResponseDto("failed");
        }

        return response;
    }

    public SignUpResponseDto signUp(SignUpDto dto){
        Optional<User> users = usersRepository.findByEmail(dto.email());
        if (users.isPresent()){
            return new SignUpResponseDto("email already exist");
        }

        try {
            User user = usersConverter.convert(dto);
            user.setAddress(getAddress(dto));
            user.setPayment(getPayment(dto));
            usersRepository.save(user);
        }
        catch (Exception e){
            return new SignUpResponseDto("failed");
        }

        return new SignUpResponseDto("success");
    }

    private Address getAddress(SignUpDto dto){
        Optional<Address> address = addressService.findAddress(dto.address().address(), dto.address().city(), dto.address().telephone());
        return (address.isPresent() ? address.get() : addressConverter.convert(dto));
    }

    private Payment getPayment(SignUpDto dto){
        Optional<Payment> payment = paymentService.findPayment(dto.payment().type(), dto.payment().accountNumber());
        return (payment.isPresent() ? payment.get() : paymentConverter.convert(dto));
    }

}
