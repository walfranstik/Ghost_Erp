package com.ghost.erp.modules.sales.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ghost.erp.modules.sales.domain.model.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByPhone(String phone);

    boolean existsByPhone(String phone);

    List<CustomerEntity> findByNameContainingIgnoreCaseAndActiveTrue(String name);
}

