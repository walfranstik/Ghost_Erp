package com.ghost.erp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ghost.erp.modules.users.domain.model.UserEntity;
import com.ghost.erp.modules.users.domain.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Leemos los valores del application.properties
    @Value("${app.admin.username:admin}")
    private String adminUsername;

    @Value("${app.admin.email:admin@gmail.com}")
    private String adminEmail;

    @Value("${app.admin.password:admin123}")
    private String adminPassword;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            logger.info(">>> No se encontraron usuarios. Creando usuario administrador inicial...");
            
            UserEntity admin = new UserEntity();
            admin.setName(adminUsername);
            admin.setEmail(adminEmail);
            admin.setPasswordHash(passwordEncoder.encode(adminPassword));
            admin.setRole(UserEntity.Role.ADMIN);
            admin.setActive(true);
            
            userRepository.save(admin);
            
            logger.info(">>> Usuario '{}' creado exitosamente.", adminUsername);
        } else {
            logger.debug(">>> La base de datos ya contiene usuarios, omitiendo inicialización.");
        }
    }
}

