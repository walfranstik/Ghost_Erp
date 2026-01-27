package com.ghost.erp.modules.inventory.domain.model.inventory;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import com.ghost.erp.modules.inventory.domain.model.product.ProductVariantEntity;

@Entity
@Table(
    name = "inventory_alerts",
    indexes = {
        @Index(name = "idx_alert_product", columnList = "product_variant_id"),
        @Index(name = "idx_alert_status", columnList = "status"),
        @Index(name = "idx_alert_created", columnList = "created_at")
    }
)
@Getter
@Setter
@Builder // Para crear objetos de forma elegante
@NoArgsConstructor // Requerido por JPA
@AllArgsConstructor // Requerido por @Builder
public class InventoryAlertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariantEntity productVariant;

    @Column(nullable = false)
    private Integer currentStock;

    @Column(nullable = false)
    private Integer minStock;

    @Column(nullable = false)
    private Integer quantityToMax;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AlertStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public enum AlertStatus {
        PENDING,
        RESOLVED
    }
}
