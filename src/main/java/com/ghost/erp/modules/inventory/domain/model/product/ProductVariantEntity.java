package com.ghost.erp.modules.inventory.domain.model.product;

import java.util.List;

import com.ghost.erp.common.base_class.BaseEntity;
import com.ghost.erp.modules.inventory.domain.model.inventory.InventoryEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(
    name = "product_variants",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_variant_sku", columnNames = "sku"),
        @UniqueConstraint(
            name = "uk_product_option", 
            columnNames = {"product_id", "attribute_option_id"}
        )
    }
)
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class ProductVariantEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(nullable = false, length = 30)
    private String sku; 

    // Aquí está la clave: La variante ahora apunta a una opción (ej: "Azul")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attribute_option_id", nullable = false)
    private AttributeOptionEntity attributeOption;

    @OneToMany(mappedBy = "productVariant")
    private List<InventoryEntity> inventories;

    
    
}