package com.ecommerce.microservices.controller;

import com.ecommerce.microservices.dto.AuthRequest;
import com.ecommerce.microservices.dto.ServiceResponse;
import com.ecommerce.microservices.entity.UserCredential;
import com.ecommerce.microservices.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ServiceResponse addUser(@RequestBody UserCredential userCredential) {
        return authService.saveUser(userCredential);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/token")
    public ServiceResponse getToken(@RequestBody AuthRequest authRequest){
        return authService.generateToken(authRequest.getUsername(), authRequest.getPassword());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/validate")
    public ServiceResponse validateToken(@RequestParam("token") String token){
        return authService.validateToken(token);
    }
}
