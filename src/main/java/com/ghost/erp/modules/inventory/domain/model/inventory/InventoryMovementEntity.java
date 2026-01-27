package com.ghost.erp.modules.inventory.domain.model.inventory;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import com.ghost.erp.common.base_class.BaseEntity;


@Entity
@Table(
    name = "inventory_movements",
    indexes = {
        @Index(name = "idx_movement_inventory", columnList = "inventory_id"),
        @Index(name = "idx_movement_type_created", columnList = "movement_type , created_at")
    }
)
@Getter
@Setter
@Builder // Para crear objetos de forma elegante
@NoArgsConstructor // Requerido por JPA
@AllArgsConstructor // Requerido por @Builder
public class InventoryMovementEntity extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inventory_id", nullable = false)
    private InventoryEntity inventory;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type",nullable = false, length = 20)
    private MovementType movementType;

    @Column(nullable = false)
    private Integer quantity;

    @Column()
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type",nullable = false, length = 20)
    private OperationType operationType;

    public enum OperationType 
        { INCREMENT, //+ 
          DECREMENT //- 
        }

    public enum MovementType {
        PURCHASE,    // Entrada por compra a proveedor (+)
        SALE,        // Salida por venta a cliente (-)
        RETURN,      // Entrada por devolución de cliente (+)
        ADJUSTMENT,  // Corrección manual (Puede ser + o -)
        TRANSFER,    // Movimiento entre bodegas (Sale de una, entra a otra)
        REVERSAL     // Cancelación de un movimiento previo (ej. anular una factura)
    }
}
