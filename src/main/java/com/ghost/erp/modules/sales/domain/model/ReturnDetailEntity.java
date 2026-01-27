package com.ghost.erp.modules.sales.domain.model;

import com.ghost.erp.modules.inventory.domain.model.product.ProductVariantEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "return_details",
    indexes = {
        @Index(name = "idx_return_detail_return", columnList =  "return_id"),
        @Index(name = "idx_return_detail_product", columnList = "product_variant_id")
    }
)
@Getter
@Setter
@Builder // Para crear objetos de forma elegante
@NoArgsConstructor // Requerido por JPA
@AllArgsConstructor // Requerido por @Builder
public class ReturnDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "return_id", nullable = false)
    private ReturnEntity returnEntry;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariantEntity productVariant;

    @Column(nullable = false)
    private Integer quantity;
}
