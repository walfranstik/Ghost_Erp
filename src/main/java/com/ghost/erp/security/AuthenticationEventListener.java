package com.ghost.erp.security;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.ghost.erp.modules.users.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationEventListener {

    private final UserRepository userRepository;
    private final LoginAttemptService loginAttemptService;

    /**
     * LOGIN EXITOSO
     */
    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {

        String email = event.getAuthentication().getName();

        userRepository.findByEmail(email)
            .ifPresent(loginAttemptService::loginSucceeded);
    }

    /**
     * LOGIN FALLIDO (password incorrecta)
     */
    @EventListener
    public void onFailure(AuthenticationFailureBadCredentialsEvent event) {

        String email = (String) event.getAuthentication().getPrincipal();

        userRepository.findByEmail(email)
            .ifPresent(loginAttemptService::loginFailed);
    }
}
