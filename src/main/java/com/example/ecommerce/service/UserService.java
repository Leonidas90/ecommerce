package com.example.ecommerce.service;

import com.example.ecommerce.converter.SignUpDtoToEntityConverter;
import com.example.ecommerce.dto.user.SignInDto;
import com.example.ecommerce.dto.user.SignInResponseDto;
import com.example.ecommerce.dto.user.SignUpDto;
import com.example.ecommerce.dto.user.SignUpResponseDto;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository usersRepository;
    private final SignUpDtoToEntityConverter usersConverter;

    public UserService(UserRepository usersRepository, SignUpDtoToEntityConverter usersConverter) {
        this.usersRepository = usersRepository;
        this.usersConverter = usersConverter;
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
            return new SignUpResponseDto("failed");
        }

        try {
            User user = usersRepository.save(usersConverter.convert(dto));
        }
        catch (Exception e){
            return new SignUpResponseDto("failed");
        }

        return new SignUpResponseDto("success");
    }
}
