package com.pelada.api.service.auth;

import com.pelada.api.dto.request.LoginRequest;
import com.pelada.api.dto.request.RefreshTokenRequest;
import com.pelada.api.dto.request.RegisterRequest;
import com.pelada.api.dto.response.AuthenticationResponse;
import com.pelada.api.dto.response.UserResponse;
import com.pelada.api.entity.User;
import com.pelada.api.enums.AuthProvider;
import com.pelada.api.enums.UserStatus;
import com.pelada.api.exception.BusinessException;
import com.pelada.api.security.JwtService;
import com.pelada.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(RegisterRequest request) {
        if (userService.existsByEmail(request.email())) {
            throw new BusinessException("Email already registered.");
        }

        User user = User.builder()
                .email(request.email().toLowerCase())
                .passwordHash(passwordEncoder.encode(request.password()))
                .authProvider(AuthProvider.LOCAL)
                .role(request.role())
                .status(UserStatus.ACTIVE)
                .build();

        User savedUser = userService.save(user);
        return new UserResponse(savedUser.getId(), savedUser.getEmail(), savedUser.getRole(), savedUser.getStatus());
    }

    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email().toLowerCase(), request.password()));
        User user = userService.findByEmail(request.email().toLowerCase()).orElseThrow(() -> new UsernameNotFoundException("Invalid email or password."));
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        String email = jwtService.extractUsername(request.refreshToken());
        User user = userService.findByEmail(email.toLowerCase()).orElseThrow(() -> new UsernameNotFoundException("User not found."));

        if (!jwtService.isTokenValid(request.refreshToken(), user)) {
            throw new BadCredentialsException("Invalid refresh token.");
        }

        return new AuthenticationResponse(jwtService.generateAccessToken(user), jwtService.generateRefreshToken(user));
    }
}