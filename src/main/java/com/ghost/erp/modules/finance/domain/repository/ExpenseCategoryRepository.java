package com.ghost.erp.modules.finance.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ghost.erp.modules.finance.domain.model.ExpenseCategoryEntity;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategoryEntity, Long> {

    Optional<ExpenseCategoryEntity> findByNameIgnoreCase(String name);

    List<ExpenseCategoryEntity> findAllByActiveTrue();
}
