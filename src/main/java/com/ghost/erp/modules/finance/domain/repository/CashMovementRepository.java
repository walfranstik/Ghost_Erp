package com.ghost.erp.modules.finance.domain.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.ghost.erp.modules.finance.domain.model.CashMovementEntity;
import com.ghost.erp.modules.finance.domain.model.CashMovementEntity.CashMovementType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CashMovementRepository extends JpaRepository<CashMovementEntity, Long> {


        /*
        * =========================================================
        * CONSULTAS BÁSICAS
        * =========================================================
        */


        // Todos los movimientos por tipo, ordenados por fecha (útil para listados ERP)
        List<CashMovementEntity> findAllByCashMovementTypeOrderByCreatedAtDesc(
        CashMovementType type
        );


        // Movimientos por rango de fechas
        List<CashMovementEntity> findAllByCreatedAtBetweenOrderByCreatedAtDesc(
        LocalDateTime from,
        LocalDateTime to
        );


        /*
        * =========================================================
        * CÁLCULOS FINANCIEROS FLEXIBLES (ERP-READY)
        * =========================================================
        */


        // Total por tipo (INCOME / OUTCOME) con rango opcional
        @Query("""
        select coalesce(sum(cm.amount), 0)
        from CashMovementEntity cm
        where cm.cashMovementType = :type
        and (:from is null or cm.createdAt >= :from)
        and (:to is null or cm.createdAt <= :to)
        """)
        BigDecimal totalByTypeAndDateRange(
        @Param("type") CashMovementType type,
        @Param("from") LocalDateTime from,
        @Param("to") LocalDateTime to
        );


        // Balance (ingresos - egresos) con rango opcional
        @Query("""
        select
        coalesce(sum(case when cm.cashMovementType = 'INCOME' then cm.amount end), 0)
        - coalesce(sum(case when cm.cashMovementType = 'OUTCOME' then cm.amount end), 0)
        from CashMovementEntity cm
        where (:from is null or cm.createdAt >= :from)
        and (:to is null or cm.createdAt <= :to)
        """)
        BigDecimal balanceByDateRange(
        @Param("from") LocalDateTime from,
        @Param("to") LocalDateTime to
        );


}

