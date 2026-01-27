package com.ghost.erp.modules.sales.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ghost.erp.modules.sales.domain.model.SaleEntity;

public interface SaleRepository extends JpaRepository<SaleEntity, Long> {

    @EntityGraph(attributePaths = {"details", "details.product"})
    Optional<SaleEntity> findWithDetailsById(Long id);

    List<SaleEntity> findAllByCustomerId(Long customerId);

    List<SaleEntity> findAllByCustomerIdAndStatus(Long customerId,SaleEntity.SaleStatus status);

    List<SaleEntity> findAllBySellerId(Long sellerId);

    List<SaleEntity> findAllBySellerIdAndStatus(Long sellerId, SaleEntity.SaleStatus status);

    List<SaleEntity> findAllByStatus(SaleEntity.SaleStatus status);

    List<SaleEntity> findAllByCreatedAtBetween(
        LocalDateTime from,
        LocalDateTime to
    );
}
