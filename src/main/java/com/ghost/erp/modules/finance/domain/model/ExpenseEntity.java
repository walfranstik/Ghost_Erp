package com.ghost.erp.modules.finance.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.ghost.erp.common.base_class.BaseEntity;
import com.ghost.erp.modules.users.domain.model.UserEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "expenses",
    indexes = {
        @Index(name = "idx_expense_cat_date", columnList = "category_id, expense_date"),
        @Index(name = "idx_expense_date", columnList = "expense_date"),
        @Index(name = "idx_expense_status_date", columnList = "status, expense_date"),
        @Index(name = "idx_expense_cash_movement", columnList = "cash_movement_id")
    }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseEntity extends BaseEntity {

    /* =====================================================
     * RELACIONES
     * ===================================================== */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private ExpenseCategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private UserEntity registeredBy; // Auditoría: quién registró el gasto

    /* =====================================================
     * DATOS FINANCIEROS
     * ===================================================== */

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "expense_date", nullable = false)
    private LocalDate expenseDate; // Fecha real del gasto (factura/recibo)

    /* =====================================================
     * METADATOS
     * ===================================================== */

    @Column(nullable = false, length = 255)
    private String description;

    @Column(length = 255)
    private String receiptUrl; // Foto o PDF del comprobante

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ExpenseStatus status = ExpenseStatus.PAID;

    /**
     * Relación lógica con CashMovement.
     * No se modela como @ManyToOne para evitar acoplamiento fuerte
     * y permitir gastos pendientes sin impacto en caja.
     */
    @Column(name = "cash_movement_id")
    private Long cashMovementId;

    /* =====================================================
     * VALIDACIONES DE DOMINIO
     * ===================================================== */

    @PrePersist
    @PreUpdate
    private void validate() {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalStateException("El monto del gasto debe ser mayor a cero");
        }

        if (expenseDate == null) {
            throw new IllegalStateException("La fecha del gasto es obligatoria");
        }
    }

    /* =====================================================
     * ENUMS
     * ===================================================== */

    public enum ExpenseStatus {
        PENDING,   // Registrado pero no pagado (Cuenta por pagar)
        PAID,      // Pagado y reflejado en caja
        CANCELLED  // Anulado (no impacta finanzas)
    }
}
