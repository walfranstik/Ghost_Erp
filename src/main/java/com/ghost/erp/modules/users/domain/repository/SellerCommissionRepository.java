package com.ghost.erp.modules.users.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ghost.erp.modules.users.domain.model.SellerCommissionEntity;

public interface SellerCommissionRepository extends JpaRepository<SellerCommissionEntity, Long> {

    Optional<SellerCommissionEntity> findBySaleId(Long saleId);

    List<SellerCommissionEntity> findAllBySellerId(Long sellerId);

    @Query("""
    SELECT COALESCE(SUM(sc.amount), 0)
    from SellerCommissionEntity sc
    where sc.seller.id = :sellerId
    and sc.paid = false
    """)
    BigDecimal totalPendingCommissionBySeller(@Param("sellerId") Long sellerId);
    
}
