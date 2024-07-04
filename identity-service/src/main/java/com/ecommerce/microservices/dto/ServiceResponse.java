package com.ecommerce.microservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ServiceResponse {
    private String message;
    private Boolean isSuccess = true;
    private Optional<Object> data;
}
