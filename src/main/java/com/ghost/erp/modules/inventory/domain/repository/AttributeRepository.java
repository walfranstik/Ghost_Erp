package com.ghost.erp.modules.inventory.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ghost.erp.modules.inventory.domain.model.product.AttributeEntity;

@Repository
public interface AttributeRepository extends JpaRepository<AttributeEntity, Long> {
    // Para buscar un atributo por su nombre (ej: "Color")
    Optional<AttributeEntity> findByNameIgnoreCase(String name);
}