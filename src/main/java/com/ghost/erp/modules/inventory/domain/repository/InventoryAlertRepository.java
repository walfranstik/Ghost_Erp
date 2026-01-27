package com.ghost.erp.modules.inventory.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ghost.erp.modules.inventory.domain.model.inventory.InventoryAlertEntity;

public interface InventoryAlertRepository extends JpaRepository<InventoryAlertEntity, Long> {

    List<InventoryAlertEntity> findAllByStatus(
        InventoryAlertEntity.AlertStatus status
    );

    List<InventoryAlertEntity> findAllByProductVariantId(Long productVariantId);
}
