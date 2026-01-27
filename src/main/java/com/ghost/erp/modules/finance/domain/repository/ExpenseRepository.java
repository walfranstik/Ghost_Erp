package com.ghost.erp.modules.finance.domain.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ghost.erp.modules.finance.domain.model.ExpenseEntity;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    List<ExpenseEntity> findAllByStatus(ExpenseEntity.ExpenseStatus status);

    List<ExpenseEntity> findAllByCategoryId(Long categoryId);

    List<ExpenseEntity> findAllByExpenseDateBetween(
        LocalDate from,
        LocalDate to
    );

    @Query("""
        select coalesce(sum(e.amount), 0)
        from ExpenseEntity e
        where e.expenseDate between :from and :to
    """)
    BigDecimal totalExpensesBetween(
        LocalDate from,
        LocalDate to
    );
}

