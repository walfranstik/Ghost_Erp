package com.ghost.erp.modules.inventory.domain.model.product;

import com.ghost.erp.common.base_class.BaseEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "attribute_options", indexes = {
    @Index(name = "idx_attr_option_val", columnList = "attribute_id"),
    @Index(name = "idx_attr_val", columnList = "value")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AttributeOptionEntity extends BaseEntity {
    
    @Column(nullable = false, length = 30)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attribute_id", nullable = false, foreignKey = @ForeignKey(name = "FK_OPTION_ATTRIBUTE"))
    private AttributeEntity attribute;


}