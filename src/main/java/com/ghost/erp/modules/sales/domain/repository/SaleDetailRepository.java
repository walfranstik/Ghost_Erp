package com.ghost.erp.modules.sales.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ghost.erp.modules.sales.domain.model.SaleDetailEntity;

public interface SaleDetailRepository extends JpaRepository<SaleDetailEntity, Long> {

    List<SaleDetailEntity> findAllBySaleId(Long saleId);

    @Query("""
        select coalesce(sum(sd.subtotal), 0)
        from SaleDetailEntity sd
        where sd.sale.id = :saleId
    """)
    BigDecimal calculateTotalBySale(Long saleId);
}
