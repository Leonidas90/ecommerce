package com.example.ecommerce.service;

import com.example.ecommerce.converter.SignUpDtoToEntityConverter;
import com.example.ecommerce.dto.user.SignInDto;
import com.example.ecommerce.dto.user.SignInResponseDto;
import com.example.ecommerce.dto.user.SignUpDto;
import com.example.ecommerce.dto.user.SignUpResponseDto;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final SignUpDtoToEntityConverter converter;

    public UserService(UserRepository repository, SignUpDtoToEntityConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public SignInResponseDto signIn(SignInDto dto){
        List<User> users = repository.findByEmail(dto.email());
        if (users.isEmpty()){
            return new SignInResponseDto("failed");
        }

        SignInResponseDto response;
        if (users.get(0).getPassword().equals(dto.password())){
            response = new SignInResponseDto("success");
        }
        else{
            response = new SignInResponseDto("failed");
        }

        return response;
    }

    public SignUpResponseDto signUp(SignUpDto dto){
        List<User> users = repository.findByEmail(dto.email());
        if (!users.isEmpty()){
            return new SignUpResponseDto("failed");
        }

        try {
            repository.save(converter.convert(dto));
        }
        catch (Exception e){
            return new SignUpResponseDto("failed");
        }

        return new SignUpResponseDto("success");
    }
}
