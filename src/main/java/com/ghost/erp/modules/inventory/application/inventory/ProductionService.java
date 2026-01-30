package com.ghost.erp.modules.inventory.application.inventory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghost.erp.modules.inventory.domain.model.ProductionEntity;
import com.ghost.erp.modules.inventory.domain.model.product.ProductVariantEntity;
import com.ghost.erp.modules.inventory.domain.repository.ProductionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ProductionService {
 
    private final ProductionRepository productionRepository;
    private final InventoryService inventoryService;

    public ProductionEntity create(ProductionEntity production) {

        if (productionRepository.existsByBatchNumber(
                production.getBatchNumber())) {
            throw new RuntimeException("Batch already exists");
        }

        return productionRepository.save(production);
    }

    public void registerProductionStock(
            ProductionEntity production,
            ProductVariantEntity variant,
            Integer quantity
    ) {

        inventoryService.createInventory(
            variant,
            production,
            quantity,
            production.getUnitCost()
        );
    }
}
