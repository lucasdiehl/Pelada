package com.pelada.api.service.impl;

import com.pelada.api.dto.filter.UserFilter;
import com.pelada.api.entity.User;
import com.pelada.api.enums.UserStatus;
import com.pelada.api.exception.ResourceNotFoundException;
import com.pelada.api.repository.UserRepository;
import com.pelada.api.repository.specification.UserSpecification;
import com.pelada.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void deleteProfile(User user) {
        user.setStatus(UserStatus.INACTIVE);
        this.save(user);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Goalkeeper profile not found."));
    }

    @Override
    public Page<User> findAll(UserFilter filter, Pageable pageable) {
        return userRepository.findAll(UserSpecification.withFilters(filter), pageable);
    }
}
