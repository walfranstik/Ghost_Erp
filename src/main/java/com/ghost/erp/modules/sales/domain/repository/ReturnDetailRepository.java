package com.ghost.erp.modules.sales.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ghost.erp.modules.sales.domain.model.ReturnDetailEntity;

public interface ReturnDetailRepository extends JpaRepository<ReturnDetailEntity, Long> {

    List<ReturnDetailEntity> findAllByReturnEntryId(Long returnId);
}

