package com.ghost.erp.modules.finance.domain.model;


import java.math.BigDecimal;

import com.ghost.erp.common.base_class.BaseEntity;
import com.ghost.erp.modules.users.domain.model.UserEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "cash_movements",
    indexes = {
        // Índice útil para balances por fecha
        @Index(name = "idx_cash_reference_type_date", 
               columnList = "reference_type , created_at"),
        @Index(name = "idx_cash_movement_type_date", 
               columnList = "cash_movement_type , created_at"),
        @Index(name = "idx_cash_movement_date", 
               columnList = "created_at")
    }
)
@Getter
@Setter
@Builder // Para crear objetos de forma elegante
@NoArgsConstructor // Requerido por JPA
@AllArgsConstructor // Requerido por @Builder
public class CashMovementEntity extends BaseEntity { // Heredamos de BaseEntity

    @Enumerated(EnumType.STRING)
    @Column(name = "cash_movement_type",nullable = false, length = 20)
    private CashMovementType cashMovementType;

    @Column(nullable = false, length = 150)
    private String concept;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    // --- MEJORAS DE SEGURIDAD E INTEGRIDAD ---

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private UserEntity user; // Quién registró el movimiento

    @Column(name = "reference_id")
    private Long referenceId; // ID de la venta, compra o gasto asociado

    @Enumerated(EnumType.STRING)
    @Column(name = "reference_type",nullable = false, length = 20)
    private ReferenceType referenceType;

    // --- VALIDACIÓN DE NEGOCIOS ---
    @PrePersist
    @PreUpdate
    void validateAmount() {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalStateException("El monto debe ser mayor a cero");
        }
    }


    public enum CashMovementType {
        INCOME, OUTCOME
    }

    public enum ReferenceType {
        SALE, PURCHASE, EXPENSE , RETURN , COMMISSION
    }
}