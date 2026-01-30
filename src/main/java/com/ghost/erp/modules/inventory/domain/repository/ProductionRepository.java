package com.ghost.erp.modules.inventory.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ghost.erp.modules.inventory.domain.model.ProductionEntity;

@Repository
public interface ProductionRepository extends JpaRepository<ProductionEntity, Long> {

    // 1. Buscar por el número de lote (como es único, devuelve un Optional)
    Optional<ProductionEntity> findByBatchNumber(String batchNumber);

    // 2. Verificar si ya existe un lote antes de intentar guardar (evita excepciones feas)
    boolean existsByBatchNumber(String batchNumber);

    // 3. Consulta con JOIN FETCH para evitar el problema de N+1
    // Trae la producción y sus detalles de inventario en un solo golpe a la DB
    @Query("SELECT p FROM ProductionEntity p LEFT JOIN FETCH p.inventorys WHERE p.id = :id")
    Optional<ProductionEntity> findByIdWithInventorys(@Param("id") Long id);

    // 4. Buscar producciones con costos mayores a cierto valor
    List<ProductionEntity> findByUnitCostGreaterThan(BigDecimal cost);
}