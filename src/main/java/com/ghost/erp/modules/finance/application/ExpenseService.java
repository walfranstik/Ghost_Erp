package com.ghost.erp.modules.finance.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghost.erp.modules.finance.domain.model.CashMovementEntity;
import com.ghost.erp.modules.finance.domain.model.ExpenseEntity;
import com.ghost.erp.modules.finance.domain.repository.ExpenseRepository;
import com.ghost.erp.modules.users.domain.model.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ExpenseService {

    private final ExpenseRepository expenseRepo;
    private final CashMovementService cashService;

    public ExpenseEntity create(ExpenseEntity expense, UserEntity user) {

        ExpenseEntity saved = expenseRepo.save(expense);

        if (expense.getStatus() == ExpenseEntity.ExpenseStatus.PAID) {

            CashMovementEntity movement =
                cashService.registerOutcome(
                    expense.getAmount(),
                    expense.getDescription(),
                    user,
                    saved.getId(),
                    CashMovementEntity.ReferenceType.EXPENSE
                );

            saved.setCashMovementId(movement.getId());
        }

        return saved;
    }

    public void markAsPaid(Long expenseId, UserEntity user) {

        ExpenseEntity expense =
            expenseRepo.findById(expenseId)
                .orElseThrow();

        CashMovementEntity movement =
            cashService.registerOutcome(
                expense.getAmount(),
                expense.getDescription(),
                user,
                expense.getId(),
                CashMovementEntity.ReferenceType.EXPENSE
            );

        expense.setStatus(ExpenseEntity.ExpenseStatus.PAID);
        expense.setCashMovementId(movement.getId());
    }
}
