package com.ghost.erp.modules.purchases.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

import com.ghost.erp.common.base_class.BaseEntity;
import com.ghost.erp.modules.inventory.domain.model.ProductionEntity;

@Entity
@Table(
    name = "purchases",
    indexes = {
        @Index(name="idx_purchase_production",columnList = "production_id"),
        @Index(name="idx_purchase_type",columnList = "type")
    }
)
@Getter
@Setter
@Builder // Para crear objetos de forma elegante
@NoArgsConstructor // Requerido por JPA
@AllArgsConstructor // Requerido por @Builder
public class PurchaseEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "production_id",nullable = false)
    private ProductionEntity production;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)
    private SupplierEntity supplier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PurchaseType type;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    // ================= RELATIONSHIPS =================

    
    public enum PurchaseType {
        // Mercancía lista para la venta (Revender)
        RESALE_GOODS,      
        
        // Materia prima (Para transformar en un producto final)
        RAW_MATERIAL,      
        
        // Insumos de producción (Cosas necesarias para producir pero que no son el producto en sí, ej: empaques)
        PRODUCTION_SUPPLIES,
        
        // Activo Fijo (Compraste una maquinaria o mueble que es para la empresa, no para vender)
        FIXED_ASSET        
    }
}