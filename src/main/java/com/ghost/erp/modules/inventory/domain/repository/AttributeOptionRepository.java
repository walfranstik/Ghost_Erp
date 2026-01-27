package com.ghost.erp.modules.inventory.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ghost.erp.modules.inventory.domain.model.product.AttributeOptionEntity;

@Repository
public interface AttributeOptionRepository extends JpaRepository<AttributeOptionEntity, Long> {
    
    // Lista todas las opciones de un atributo específico (ej: todos los colores)
    List<AttributeOptionEntity> findByAttributeId(Long attributeId);

}