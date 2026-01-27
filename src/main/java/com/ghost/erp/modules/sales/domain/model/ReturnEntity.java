package com.ghost.erp.modules.sales.domain.model;

import java.util.ArrayList;
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
    name = "returns",
    indexes = {
        @Index(name = "idx_return_sale", columnList = "sale_id"),
        @Index(name = "idx_return_status", columnList = "status")
    }
)
@Getter
@Setter
@Builder // Para crear objetos de forma elegante
@NoArgsConstructor // Requerido por JPA
@AllArgsConstructor // Requerido por @Builder
public class ReturnEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sale_id", nullable = false)
    private SaleEntity sale;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReturnStatus status;

    @OneToMany(
        mappedBy = "returnEntry",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @Builder.Default
    private List<ReturnDetailEntity> details = new ArrayList<>();

    public enum ReturnStatus {
        PENDING,
        RECEIVED,
        REFUNDED
    }
}
