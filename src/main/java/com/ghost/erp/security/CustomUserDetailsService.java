package com.ghost.erp.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ghost.erp.modules.users.domain.model.UserEntity;
import com.ghost.erp.modules.users.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final LoginAttemptService loginAttemptService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserEntity user = userRepository.findByEmail(email)
            .filter(UserEntity::isActive)
            .orElseThrow(() -> new UsernameNotFoundException("Credenciales inválidas"));

        if (loginAttemptService.isLocked(user)) {
            System.out.println("******cuenta bloqueada temporalmente******");
        }

        return new UserPrincipal(user);
    }
}
