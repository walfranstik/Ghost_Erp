package com.ghost.erp.modules.users.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ghost.erp.common.base_class.BaseEntity;
import com.ghost.erp.modules.sales.domain.model.SaleEntity;

import java.math.BigDecimal;


@Entity
@Table(
    name = "seller_commissions",
    uniqueConstraints = {
         @UniqueConstraint(
            name = "uk_commission_sale_seller",
            columnNames = {"sale_id", "seller_id"}
        )
    },
    indexes = {
        @Index(name = "idx_commission_seller_paid", columnList = "seller_id, paid")
    }
)
@Getter
@Setter
@Builder // Para crear objetos de forma elegante
@NoArgsConstructor // Requerido por JPA
@AllArgsConstructor // Requerido por @Builder
public class SellerCommissionEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seller_id", nullable = false)
    private UserEntity seller;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sale_id", nullable = false)
    private SaleEntity sale;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Builder.Default
    @Column(nullable = false)
    private boolean paid = false;

}
