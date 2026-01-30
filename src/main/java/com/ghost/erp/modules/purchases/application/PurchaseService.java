package com.ghost.erp.modules.purchases.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghost.erp.modules.finance.application.CashMovementService;
import com.ghost.erp.modules.finance.domain.model.CashMovementEntity;
import com.ghost.erp.modules.purchases.domain.model.PurchaseEntity;
import com.ghost.erp.modules.purchases.domain.repository.PurchaseRepository;
import com.ghost.erp.modules.users.domain.model.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class PurchaseService {

    private final PurchaseRepository purchaseRepo;
    private final CashMovementService cashService;

    public PurchaseEntity create(PurchaseEntity purchase, UserEntity user) {

        PurchaseEntity saved = purchaseRepo.save(purchase);

        cashService.registerOutcome(
            purchase.getTotal(),
            "Purchase to supplier: " + purchase.getSupplier().getName(),
            user,
            saved.getId(),
            CashMovementEntity.ReferenceType.PURCHASE
        );

        return saved;
    }
}

