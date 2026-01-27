package com.ghost.erp.modules.inventory.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ghost.erp.modules.inventory.domain.model.product.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByCode(String code);

    boolean existsByCode(String code);

    List<ProductEntity> findByNameContainingIgnoreCaseAndActiveTrue(String name);

    List<ProductEntity> findAllByActiveTrue();

}

