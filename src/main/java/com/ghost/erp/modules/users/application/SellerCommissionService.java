package com.ghost.erp.modules.users.application;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghost.erp.modules.finance.application.CashMovementService;
import com.ghost.erp.modules.finance.domain.model.CashMovementEntity;
import com.ghost.erp.modules.sales.domain.model.SaleEntity;
import com.ghost.erp.modules.users.domain.model.SellerCommissionEntity;
import com.ghost.erp.modules.users.domain.model.UserEntity;
import com.ghost.erp.modules.users.domain.repository.SellerCommissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SellerCommissionService {

    private final SellerCommissionRepository repo;
    private final CashMovementService cashService;

    public SellerCommissionEntity createCommission(
            SaleEntity sale,
            BigDecimal amount
    ) {

        return repo.save(
            SellerCommissionEntity.builder()
                .sale(sale)
                .seller(sale.getSeller())
                .amount(amount)
                .build()
        );
    }

    public void payCommission(
            Long commissionId,
            UserEntity user
    ) {

        SellerCommissionEntity commission =
            repo.findById(commissionId)
                .orElseThrow();

        cashService.registerOutcome(
            commission.getAmount(),
            "Commission payment",
            user,
            commission.getId(),
            CashMovementEntity.ReferenceType.COMMISSION
        );

        commission.setPaid(true);
    }
}
