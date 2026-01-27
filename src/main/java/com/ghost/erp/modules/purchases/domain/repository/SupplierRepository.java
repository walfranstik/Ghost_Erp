package com.ghost.erp.modules.purchases.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ghost.erp.modules.purchases.domain.model.SupplierEntity;

public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {

    Optional<SupplierEntity> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}

