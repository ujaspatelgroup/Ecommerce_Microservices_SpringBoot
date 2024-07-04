package com.ecommerce.microservices.service;

import com.ecommerce.microservices.dto.AuthResponse;
import com.ecommerce.microservices.dto.ServiceResponse;
import com.ecommerce.microservices.entity.UserCredential;
import com.ecommerce.microservices.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ServiceResponse serviceResponse;

    public ServiceResponse saveUser(UserCredential userCredential) {
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        userCredentialRepository.save(userCredential);
        serviceResponse.setMessage("User Saved");
        serviceResponse.setIsSuccess(true);
        serviceResponse.setData(Optional.empty());
        return serviceResponse;
    }

    public ServiceResponse generateToken(String username, String password) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if (authenticate.isAuthenticated()) {

            AuthResponse authResponse = new AuthResponse();
            authResponse.setToken(jwtService.generateToken(username));
            authResponse.setUsername(username);

            serviceResponse.setMessage("Login Successful");
            serviceResponse.setIsSuccess(true);
            serviceResponse.setData(Optional.of(authResponse));
            return serviceResponse;
        }
        else {
            serviceResponse.setMessage("Authentication Failed");
            serviceResponse.setIsSuccess(false);
            serviceResponse.setData(Optional.empty());

            return serviceResponse;
        }
    }

    public ServiceResponse validateToken(String token) {
        serviceResponse.setMessage("Token is valid");
        serviceResponse.setIsSuccess(true);
        serviceResponse.setData(Optional.empty());
        jwtService.validateToken(token);
        return serviceResponse;
    }
}
