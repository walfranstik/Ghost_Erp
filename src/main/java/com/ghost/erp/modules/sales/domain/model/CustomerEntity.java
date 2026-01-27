package com.ghost.erp.modules.sales.domain.model;

import com.ghost.erp.common.base_class.BaseEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(
    name = "customers",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_customer_phone", columnNames = "phone")
    },
    indexes = {
        @Index(name = "idx_customer_phone", columnList = "phone"),
        @Index(name = "idx_customer_active", columnList = "active"),
    }
)
@Getter
@Setter
@Builder // Para crear objetos de forma elegante
@NoArgsConstructor // Requerido por JPA
@AllArgsConstructor // Requerido por @Builder
public class CustomerEntity extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 150)
    private String email;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(length = 255)
    private String address;

    
}
