package com.ghost.erp.modules.inventory.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ghost.erp.modules.inventory.domain.model.product.ProductVariantEntity;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity, Long> {

    // Buscar una variante por su código único
    Optional<ProductVariantEntity> findBySku(String sku);


    // Listar todas las variantes de un producto (ej: todos los tamaños y colores de un Polo)
    List<ProductVariantEntity> findByProductId(Long productId);

    // Verificar si ya existe un SKU antes de crearlo
    boolean existsBySku(String sku);

}
