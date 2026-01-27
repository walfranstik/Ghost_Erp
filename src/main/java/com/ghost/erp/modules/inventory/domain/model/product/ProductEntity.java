package com.ghost.erp.modules.inventory.domain.model.product;



import java.util.List;

import com.ghost.erp.common.base_class.BaseEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "products",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_product_code", columnNames = "code")
    },
    indexes = {
        @Index(name = "idx_product_name", columnList = "name"),
        @Index(name = "idx_product_active", columnList = "active")
    }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity extends BaseEntity {

    @Column(nullable = false, length = 50, updatable = false)
    private String code; // Ej: POLO-ROJO

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 255)
    private String description;

    // Mínimo por producto (estratégico)
    @Column(nullable = false)
    private Integer minStock;

    @Column(nullable = false)
    private Integer maxStock;

    // Variantes (S, M, L, XL)
    @OneToMany(
        mappedBy = "product",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<ProductVariantEntity> variants;
}
