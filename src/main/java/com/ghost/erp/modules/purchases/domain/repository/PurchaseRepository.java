package com.ghost.erp.modules.purchases.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ghost.erp.modules.purchases.domain.model.PurchaseEntity;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {

    List<PurchaseEntity> findAllBySupplierId(Long supplierId);

    List<PurchaseEntity> findAllByProductionId(Long productionId);

    List<PurchaseEntity> findAllByType(PurchaseEntity.PurchaseType type);
}
