package com.ghost.erp.modules.users.application;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghost.erp.modules.users.domain.model.UserEntity;
import com.ghost.erp.modules.users.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /* ===========================
       CREATE
    ============================ */

    @PreAuthorize("hasRole('ADMIN')")
    public UserEntity create(String name,
                             String email,
                             String rawPassword,
                             UserEntity.Role role) {

        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException("Email ya registrado");
        }

        UserEntity user = UserEntity.builder()
                .name(name)
                .email(email)
                .passwordHash(passwordEncoder.encode(rawPassword))
                .role(role)
                .failedLoginAttempts(0)
                .build();

        return userRepository.save(user);
    }

    /* ===========================
       UPDATE
    ============================ */

    @PreAuthorize("hasRole('ADMIN')")
    public UserEntity update(Long id,
                             String name,
                             String email,
                             UserEntity.Role role) {

        UserEntity user = findById(id);

        user.setName(name);
        user.setEmail(email);
        user.setRole(role);

        return user;
    }

    /* ===========================
       CHANGE PASSWORD
    ============================ */

    @PreAuthorize("hasRole('ADMIN')")
    public void changePassword(Long id, String rawPassword) {

        UserEntity user = findById(id);
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
    }

    /* ===========================
       DELETE (LOGICAL)
    ============================ */

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id) {

        UserEntity user = findById(id);
        user.setActive(false);
    }

    /* ===========================
       READ
    ============================ */

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserEntity> findAll() {
        return userRepository.findAllByActiveTrue();
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalStateException("Usuario no existe"));
    }
}
