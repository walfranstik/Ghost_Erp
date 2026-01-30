package com.ghost.erp.modules.inventory.application.inventory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghost.erp.modules.inventory.domain.model.ProductionEntity;
import com.ghost.erp.modules.inventory.domain.model.inventory.InventoryAlertEntity;
import com.ghost.erp.modules.inventory.domain.model.inventory.InventoryEntity;
import com.ghost.erp.modules.inventory.domain.model.inventory.InventoryMovementEntity;
import com.ghost.erp.modules.inventory.domain.model.product.ProductEntity;
import com.ghost.erp.modules.inventory.domain.model.product.ProductVariantEntity;
import com.ghost.erp.modules.inventory.domain.repository.InventoryAlertRepository;
import com.ghost.erp.modules.inventory.domain.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryAlertRepository alertRepository;
    private final InventoryMovementService movementService;

    /* ======================================
       CREAR INVENTARIO INICIAL
    ======================================= */

    public InventoryEntity createInventory(
            ProductVariantEntity variant,
            ProductionEntity production,
            Integer quantity,
            BigDecimal unitCost
    ) {

        InventoryEntity inventory =
            InventoryEntity.builder()
                .productVariant(variant)
                .production(production)
                .currentStock(quantity)
                .unitCost(unitCost)
                .build();

        inventoryRepository.save(inventory);

        movementService.registerMovement(
            inventory,
            InventoryMovementEntity.MovementType.PURCHASE,
            InventoryMovementEntity.OperationType.INCREMENT,
            quantity,
            "Initial stock"
        );

        checkLowStock(inventory);

        return inventory;
    }

    /* ======================================
       DESCONTAR STOCK (VENTA)
    ======================================= */

    public void discountStock(
            InventoryEntity inventory,
            Integer quantity
    ) {

        if (inventory.getCurrentStock() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        inventory.setCurrentStock(
            inventory.getCurrentStock() - quantity
        );

        movementService.registerMovement(
            inventory,
            InventoryMovementEntity.MovementType.SALE,
            InventoryMovementEntity.OperationType.DECREMENT,
            quantity,
            "Sale"
        );

        checkLowStock(inventory);
    }

    /* ======================================
       DEVOLUCIÓN
    ======================================= */

    public void returnStock(
            InventoryEntity inventory,
            Integer quantity
    ) {

        inventory.setCurrentStock(
            inventory.getCurrentStock() + quantity
        );

        movementService.registerMovement(
            inventory,
            InventoryMovementEntity.MovementType.RETURN,
            InventoryMovementEntity.OperationType.INCREMENT,
            quantity,
            "Return"
        );
    }

    /* ======================================
       AJUSTE MANUAL
    ======================================= */

    public void adjustStock(
            InventoryEntity inventory,
            Integer quantity,
            InventoryMovementEntity.OperationType operation,
            String reason
    ) {

        if (operation == InventoryMovementEntity.OperationType.INCREMENT) {
            inventory.setCurrentStock(inventory.getCurrentStock() + quantity);
        } else {
            inventory.setCurrentStock(inventory.getCurrentStock() - quantity);
        }

        movementService.registerMovement(
            inventory,
            InventoryMovementEntity.MovementType.ADJUSTMENT,
            operation,
            quantity,
            reason
        );

        checkLowStock(inventory);
    }

    /* ======================================
       ALERTAS
    ======================================= */

    private void checkLowStock(InventoryEntity inventory) {

        ProductEntity product =
            inventory.getProductVariant()
                     .getProduct();

        if (inventory.getCurrentStock() <= product.getMinStock()) {

            InventoryAlertEntity alert =
                InventoryAlertEntity.builder()
                    .productVariant(inventory.getProductVariant())
                    .currentStock(inventory.getCurrentStock())
                    .minStock(product.getMinStock())
                    .quantityToMax(
                        product.getMaxStock()
                        - inventory.getCurrentStock()
                    )
                    .status(InventoryAlertEntity.AlertStatus.PENDING)
                    .build();

            alertRepository.save(alert);
        }
    }

    /* ======================================
       CONSULTAS
    ======================================= */

    @Transactional(readOnly = true)
    public List<InventoryEntity> findLowStock() {
        return inventoryRepository.findLowStockByInventory();
    }

     @Transactional(readOnly = true)
    public Optional<InventoryEntity> findById(Long inventoryId) {
        return inventoryRepository.findById(inventoryId);
    }
}
