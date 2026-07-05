package com.pelada.api.service;

import com.pelada.api.dto.filter.UserFilter;
import com.pelada.api.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface UserService {
    boolean existsByEmail(@NotBlank(message = "Email is required") @Email(message = "Invalid email") String email);
    User save(User user);
    Optional<User> findByEmail(String lowerCase);
    void deleteProfile(User user);
    User findUserById(Long id);
    Page<User> findAll(UserFilter filter, Pageable pageable);
}
