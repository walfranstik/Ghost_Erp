package com.ghost.erp.modules.inventory.application.inventory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghost.erp.modules.inventory.domain.model.inventory.InventoryEntity;
import com.ghost.erp.modules.inventory.domain.model.inventory.InventoryMovementEntity;
import com.ghost.erp.modules.inventory.domain.repository.InventoryMovementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class InventoryMovementService {

    private final InventoryMovementRepository movementRepository;

    public void registerMovement(
            InventoryEntity inventory,
            InventoryMovementEntity.MovementType type,
            InventoryMovementEntity.OperationType operation,
            Integer quantity,
            String description
    ) {

        InventoryMovementEntity movement =
            InventoryMovementEntity.builder()
                .inventory(inventory)
                .movementType(type)
                .operationType(operation)
                .quantity(quantity)
                .description(description)
                .build();

        movementRepository.save(movement);
    }
}
