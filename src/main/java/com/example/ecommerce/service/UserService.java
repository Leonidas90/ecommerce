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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        User user = getUserByEmail((dto.email()));

        if (!user.getPassword().equals(dto.password())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "wrong password");
        }

        return new SignInResponseDto(user.getId());
    }

    public void signUp(SignUpDto dto){
        Optional<User> users = usersRepository.findByEmail(dto.email());
        if (users.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "email already exist");
        }

        User user = usersConverter.convert(dto);
        user.setAddress(getAddress(dto));
        user.setPayment(getPayment(dto));
        usersRepository.save(user);
    }

    public User getUser(String id){
        try {
            Long userId = Long.parseLong(id);
            return  usersRepository
                    .findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        }
        catch (NumberFormatException e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid product id");
        }
    }

    private Address getAddress(SignUpDto dto){
        Optional<Address> address = addressService.findAddress(dto.address().address(), dto.address().city(), dto.address().telephone());
        return (address.isPresent() ? address.get() : addressConverter.convert(dto));
    }

    private Payment getPayment(SignUpDto dto){
        Optional<Payment> payment = paymentService.findPayment(dto.payment().type(), dto.payment().accountNumber());
        return (payment.isPresent() ? payment.get() : paymentConverter.convert(dto));
    }

    private User getUserByEmail(String email){
        return usersRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
    }

}
