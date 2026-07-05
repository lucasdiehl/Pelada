package com.pelada.api.controller;

import com.pelada.api.dto.request.LoginRequest;
import com.pelada.api.dto.request.RefreshTokenRequest;
import com.pelada.api.dto.request.RegisterRequest;
import com.pelada.api.dto.response.AuthenticationResponse;
import com.pelada.api.dto.response.UserResponse;
import com.pelada.api.service.auth.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication and authorization endpoints")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody @Valid RegisterRequest request) {
        return authenticationService.register(request);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody @Valid LoginRequest request) {
        return authenticationService.login(request);
    }

    @PostMapping("/refresh")
    public AuthenticationResponse refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        return authenticationService.refreshToken(request);
    }
}