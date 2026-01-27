package com.ghost.erp.modules.users.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ghost.erp.modules.users.domain.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    List<UserEntity> findAllByActiveTrue();

    List<UserEntity> findAllByRoleAndActiveTrue(UserEntity.Role role);
}
