package com.ghost.erp.modules.sales.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ghost.erp.modules.sales.domain.model.ReturnEntity;

public interface ReturnRepository extends JpaRepository<ReturnEntity, Long> {

    @EntityGraph(attributePaths = {"details", "details.product"})
    Optional<ReturnEntity> findWithDetailsById(Long id);

    List<ReturnEntity> findAllBySaleId(Long saleId);

    List<ReturnEntity> findAllByStatus(ReturnEntity.ReturnStatus status);
}
