package com.ordersmanagement.crm.controllers;

import com.ordersmanagement.crm.models.dto.LoginForm;
import com.ordersmanagement.crm.models.dto.JwtResponse;
import com.ordersmanagement.crm.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signIn(@Valid @RequestBody LoginForm credentials) {
        JwtResponse authentication = authService.trySignIn(credentials);
        return new ResponseEntity<>(authentication, HttpStatus.OK);
    }
}