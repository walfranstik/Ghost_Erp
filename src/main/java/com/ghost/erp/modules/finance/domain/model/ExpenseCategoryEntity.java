package com.ghost.erp.modules.finance.domain.model;


import com.ghost.erp.common.base_class.BaseEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(
    name = "expense_categories",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_expense_category_name",
            columnNames = "name"
        )
    },
    indexes = {
        @Index(name = "idx_expense_category_active", columnList = "active")
    }
)
@Getter
@Setter
@Builder // Para crear objetos de forma elegante
@NoArgsConstructor // Requerido por JPA
@AllArgsConstructor // Requerido por @Builder
public class ExpenseCategoryEntity extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String description;
}
