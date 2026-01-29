package com.ghost.erp.modules.sales.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ghost.erp.modules.finance.application.CashMovementService;
import com.ghost.erp.modules.inventory.application.InventoryService;
import com.ghost.erp.modules.sales.domain.model.SaleEntity;
import com.ghost.erp.modules.sales.domain.repository.SaleRepository;
import com.ghost.erp.modules.users.application.SellerCommissionService;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SaleService {

    private final SaleRepository saleRepository;

    /* ========================
       CREATE
    ========================= */

    public SaleEntity create(SaleEntity sale) {

        sale.setStatus(SaleEntity.SaleStatus.CREATED);

        // Validación mínima
        if (sale.getDetails().isEmpty()) {
            throw new RuntimeException("Sale must contain details");
        }

        return saleRepository.save(sale);
    }

    /* ========================
       READ
    ========================= */

    @Transactional(readOnly = true)
    public List<SaleEntity> findAll() {
        return saleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public SaleEntity findById(Long id) {
        return saleRepository.findWithDetailsById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found"));
    }

    /* ========================
       UPDATE
    ========================= */

    public SaleEntity update(Long id, SaleEntity newData) {

        SaleEntity sale = findById(id);

        if (sale.getStatus() != SaleEntity.SaleStatus.CREATED) {
            throw new RuntimeException("Only CREATED sales can be edited");
        }

        sale.setCustomer(newData.getCustomer());
        sale.setSeller(newData.getSeller());
        sale.setShippingPrice(newData.getShippingPrice());
        sale.setTotal(newData.getTotal());
        sale.setDetails(newData.getDetails());

        return saleRepository.save(sale);
    }

    /* ========================
       DELETE
    ========================= */

    public void delete(Long id) {

        SaleEntity sale = findById(id);

        if (sale.getStatus() != SaleEntity.SaleStatus.CREATED) {
            throw new RuntimeException("Only CREATED sales can be deleted");
        }

        saleRepository.delete(sale);
    }
    
    private final InventoryService inventoryService;
    private final SellerCommissionService sellerCommissionService;
    private final CashMovementService cashMovementService;
    private final ReturnService returnService;

    public SaleEntity changeStatus(Long saleId, SaleEntity.SaleStatus newStatus) {

        SaleEntity sale = saleRepository.findWithDetailsById(saleId)
                .orElseThrow(() -> new RuntimeException("Sale not found"));

        switch (newStatus) {

            case DELIVERED -> handleDelivered(sale);

            case RETURNED -> handleReturned(sale);

            case CANCELLED -> handleCancelled(sale);

            default -> sale.changeStatus(newStatus);
        }

        return saleRepository.save(sale);
    }

    private void handleDelivered(SaleEntity sale) {

        // 1. Cambiar estado
        sale.changeStatus(SaleEntity.SaleStatus.DELIVERED);

        // 2. Descontar inventario
        //inventoryService.discountStockFromSale(sale);

        // 3. Crear comisión
        //sellerCommissionService.createForSale(sale);

        // 4. Movimiento de caja
        //cashMovementService.registerIncomeFromSale(sale);
    }

    private void handleReturned(SaleEntity sale) {

        sale.changeStatus(SaleEntity.SaleStatus.RETURNED);

        //returnService.createReturnFromSale(sale);

        //inventoryService.restoreStockFromSale(sale);

        //cashMovementService.registerRefund(sale);
    }

    private void handleCancelled(SaleEntity sale) {

        sale.changeStatus(SaleEntity.SaleStatus.CANCELLED);
    }
}

