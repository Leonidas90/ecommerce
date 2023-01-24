package com.example.ecommerce.controller;

import com.example.ecommerce.dto.user.SignInDto;
import com.example.ecommerce.dto.user.SignInResponseDto;
import com.example.ecommerce.dto.user.SignUpDto;
import com.example.ecommerce.dto.user.SignUpResponseDto;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("signin")
    SignInResponseDto signIn(@RequestBody SignInDto dto){
        return userService.signIn(dto);
    }

    @PostMapping("signup")
    SignUpResponseDto signIn(@RequestBody SignUpDto dto){
        return userService.signUp(dto);
    }
}