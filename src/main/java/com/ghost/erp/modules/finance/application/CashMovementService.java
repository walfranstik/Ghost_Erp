package com.ghost.erp.modules.finance.application;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghost.erp.modules.finance.domain.model.CashMovementEntity;
import com.ghost.erp.modules.finance.domain.repository.CashMovementRepository;
import com.ghost.erp.modules.users.domain.model.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CashMovementService {

    private final CashMovementRepository cashRepo;

    public CashMovementEntity registerIncome(
            BigDecimal amount,
            String concept,
            UserEntity user,
            Long referenceId,
            CashMovementEntity.ReferenceType refType
    ) {

        return cashRepo.save(
            CashMovementEntity.builder()
                .cashMovementType(CashMovementEntity.CashMovementType.INCOME)
                .amount(amount)
                .concept(concept)
                .user(user)
                .referenceId(referenceId)
                .referenceType(refType)
                .build()
        );
    }

    public CashMovementEntity registerOutcome(
            BigDecimal amount,
            String concept,
            UserEntity user,
            Long referenceId,
            CashMovementEntity.ReferenceType refType
    ) {

        return cashRepo.save(
            CashMovementEntity.builder()
                .cashMovementType(CashMovementEntity.CashMovementType.OUTCOME)
                .amount(amount)
                .concept(concept)
                .user(user)
                .referenceId(referenceId)
                .referenceType(refType)
                .build()
        );
    }

    @Transactional(readOnly = true)
    public BigDecimal getBalance() {
        return cashRepo.balanceByDateRange(null, null);
    }
}
