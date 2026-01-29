package com.ghost.erp.modules.inventory.domain.model.product;



import java.util.List;
import java.util.ArrayList;

import com.ghost.erp.common.base_class.BaseEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "categories",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_category_name", columnNames = "name")
    }
)
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class CategoryEntity extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 255)
    private String description;

    // Para categorías jerárquicas (opcional, pero muy útil en ERP)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CategoryEntity parent;

    @OneToMany(mappedBy = "parent")
    @Builder.Default
    private List<CategoryEntity> subCategories = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    @Builder.Default
    private List<ProductEntity> products = new ArrayList<>();
}