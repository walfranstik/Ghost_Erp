package com.ghost.erp.modules.inventory.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ghost.erp.modules.inventory.domain.model.inventory.InventoryMovementEntity;

public interface InventoryMovementRepository extends JpaRepository<InventoryMovementEntity, Long> {

    List<InventoryMovementEntity> findAllByInventoryId(Long productId);

    List<InventoryMovementEntity> findAllByMovementType(
        InventoryMovementEntity.MovementType type
    );

    List<InventoryMovementEntity> findAllByCreatedAtBetween(
        LocalDateTime from,
        LocalDateTime to
    );
}

