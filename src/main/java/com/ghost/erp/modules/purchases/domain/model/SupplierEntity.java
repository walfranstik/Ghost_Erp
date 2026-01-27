package com.ghost.erp.modules.purchases.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "suppliers",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
    }
)
@Getter
@Setter
@Builder // Para crear objetos de forma elegante
@NoArgsConstructor // Requerido por JPA
@AllArgsConstructor // Requerido por @Builder
public class SupplierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 150)
    private String contact;
}
