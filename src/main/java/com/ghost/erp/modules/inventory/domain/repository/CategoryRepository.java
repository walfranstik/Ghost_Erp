package com.ghost.erp.modules.inventory.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    // Buscar categorías principales (las que no tienen padre)
    List<CategoryEntity> findByParentIsNull();

    // Buscar todas las subcategorías de una categoría específica
    List<CategoryEntity> findByParentId(Long parentId);

    // Buscar por nombre (ignora mayúsculas/minúsculas)
    Optional<CategoryEntity> findByNameIgnoreCase(String name);
}
