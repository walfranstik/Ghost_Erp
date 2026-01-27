package com.ghost.erp.modules.inventory.domain.model.product;


import java.util.List;
import java.util.ArrayList;


import com.ghost.erp.common.base_class.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "attributes", indexes = {
    @Index(name = "idx_attribute_name", columnList = "name")
})
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class AttributeEntity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 30)
    private String name;

    // Mejora: Relación bidireccional con OrphanRemoval para limpieza automática
    @OneToMany(mappedBy = "attribute", cascade = CascadeType.ALL)
    @Builder.Default
    private List<AttributeOptionEntity> options = new ArrayList<>();

    // Helper method para mantener la sincronización de ambos lados de la relación
    public void addOption(AttributeOptionEntity option) {
        options.add(option);
        option.setAttribute(this);
    }
}