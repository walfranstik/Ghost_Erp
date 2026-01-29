package com.ghost.erp.modules.sales.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;

import com.ghost.erp.common.base_class.BaseEntity;
import com.ghost.erp.modules.users.domain.model.UserEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "sales",
    indexes = {
        @Index(name = "idx_sale_customer_status", columnList = "customer_id, status"),
        @Index(name = "idx_sale_seller_status_created", columnList = "seller_id, status , created_at"),
        @Index(name = "idx_sale_tracking_guide", columnList = "tracking_guide"),
        @Index(name = "idx_sale_status", columnList = "status")
    }
)
@Getter
@Setter
@Builder // Para crear objetos de forma elegante
@NoArgsConstructor // Requerido por JPA
@AllArgsConstructor // Requerido por @Builder
public class SaleEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seller_id", nullable = false)
    private UserEntity seller;

    @OneToMany(
    mappedBy = "sale",
    fetch = FetchType.LAZY,
    cascade = CascadeType.ALL,
    orphanRemoval = true
    )
    @BatchSize(size = 20)
    @Builder.Default
    private List<SaleDetailEntity> details = new ArrayList<>();

    @Column(name = "shipping_price", nullable = false, precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal shippingPrice=BigDecimal.ZERO; 

    @Column(name = "tracking_guide", length = 50)
    private String trackingGuide;

    @Column(name = "carrier_name", length = 50)
    private String carrierName; // TRANSPORTADORA Ej: "FedEx", "DHL", "Propio"

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SaleStatus status;

    public enum SaleStatus {
        CREATED,
        PENDING,
        DELIVERED,
        RETURNED,
        CANCELLED
    }

    public void changeStatus(SaleStatus newStatus) {
    if (this.status == SaleStatus.CANCELLED) {
        throw new IllegalStateException("Cannot change status of cancelled sale");
    }
    this.status = newStatus;
    }
    
    public boolean isDelivered() {
    return this.status == SaleStatus.DELIVERED;
    }


}

