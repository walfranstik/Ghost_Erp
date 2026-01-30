package com.ghost.erp.modules.inventory.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ghost.erp.common.base_class.BaseEntity;
import com.ghost.erp.modules.inventory.domain.model.inventory.InventoryEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "production",
    uniqueConstraints = 
    @UniqueConstraint(name="uk_production_batch_number",columnNames = "batch_number")
)
@Getter
@Setter
@Builder // Para crear objetos de forma elegante
@NoArgsConstructor // Requerido por JPA
@AllArgsConstructor // Requerido por @Builder
public class ProductionEntity extends BaseEntity {

    @OneToMany(
        mappedBy = "production",
        cascade = CascadeType.ALL
    )
    @Builder.Default
    private List<InventoryEntity> inventorys = new ArrayList<>();

    @Column(nullable = false)
    private Integer quantity_produced;

    @Column(name = "unit_cost",nullable = false, precision = 12, scale = 2)
    private BigDecimal unitCost;

    @Column(name = "batch_number",nullable = false, length = 255)
    private String batchNumber;

  

}

