package com.example.ecommerce.controller;

import com.example.ecommerce.dto.user.SignInDto;
import com.example.ecommerce.dto.user.SignInResponseDto;
import com.example.ecommerce.dto.user.SignUpDto;
import com.example.ecommerce.service.SessionService;
import com.example.ecommerce.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpTimeoutException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    SessionService sessionService;

    @PostMapping("login")
    public SignInResponseDto signIn(@RequestBody SignInDto dto, final HttpSession httpSession){
        SignInResponseDto response = userService.signIn(dto);
        sessionService.bindSessionToUser(response.userid(), httpSession);
        return response;
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(final HttpSession httpSession) {
        sessionService.invalidateSession(httpSession);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("signup")
    public void signIn(@RequestBody SignUpDto dto, final HttpSession httpSession){
        userService.signUp(dto);
    }
}
