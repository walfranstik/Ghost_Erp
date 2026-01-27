package com.ghost.erp.security;


import org.springframework.stereotype.Service;

import com.ghost.erp.modules.users.domain.model.UserEntity;
import com.ghost.erp.modules.users.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginAttemptService {

    private static final int MAX_ATTEMPTS = 5;
    private static final int LOCK_MINUTES = 15;

    private final UserRepository userRepository;

    public void loginFailed(UserEntity user) {
        user.increaseLoginAttempts(MAX_ATTEMPTS, LOCK_MINUTES);
        userRepository.save(user);
    }

    public void loginSucceeded(UserEntity user) {
        user.resetLoginAttempts();
        userRepository.save(user);
    }

    public boolean isLocked(UserEntity user) {
        return user.isAccountLocked();
    }
}

