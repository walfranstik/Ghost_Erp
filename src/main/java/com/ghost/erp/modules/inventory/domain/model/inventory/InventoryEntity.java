package com.ghost.erp.modules.inventory.domain.model.inventory;

import java.math.BigDecimal;

import com.ghost.erp.common.base_class.BaseEntity;
import com.ghost.erp.modules.inventory.domain.model.ProductionEntity;
import com.ghost.erp.modules.inventory.domain.model.product.ProductVariantEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "inventories",
    indexes = {
        @Index(name = "idx_inventory_product", columnList = "product_variant_id"),
        @Index(name = "idx_inventory_production", columnList = "production_id"),
        @Index(name = "idx_inventory_product_production", columnList = "product_variant_id, production_id")
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_inventory_product_production",
            columnNames = {"product_variant_id", "production_id"}
        )
    }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryEntity extends BaseEntity {

    /* =====================================================
     * RELACIONES
     * ===================================================== */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_variant_id", nullable = false, updatable = false)
    private ProductVariantEntity productVariant;

    /**
     * Lote / producción a la que pertenece el inventario.
     * Puede ser null si el producto no se controla por lote.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_id")
    private ProductionEntity production;

    /* =====================================================
     * DATOS DE INVENTARIO
     * ===================================================== */

    @Column(name = "current_stock", nullable = false)
    private Integer currentStock;

    @Column(name = "unit_cost", nullable = false, precision = 12, scale = 2)
    private BigDecimal unitCost; // Costo unitario promedio o por lote

    /* =====================================================
     * VALIDACIONES DE DOMINIO
     * ===================================================== */

    @PrePersist
    @PreUpdate
    private void validate() {
        if (currentStock == null || currentStock < 0) {
            throw new IllegalStateException("El stock no puede ser negativo");
        }

        if (unitCost == null || unitCost.signum() < 0) {
            throw new IllegalStateException("El costo unitario no puede ser negativo");
        }
    }
}
