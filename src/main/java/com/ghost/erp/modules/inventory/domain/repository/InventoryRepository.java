package com.ghost.erp.modules.inventory.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ghost.erp.modules.inventory.domain.model.inventory.InventoryEntity;
import com.ghost.erp.modules.inventory.domain.model.product.ProductEntity;

public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {

    Optional<InventoryEntity> findById(Long inventoryId);

    Optional<InventoryEntity> findByProductVariant(Long productId);
    
    @Query("""
    select i
    from InventoryEntity i
    join i.productVariant pv
    join pv.product p
    where p.minStock > 0
      and i.currentStock <= p.minStock
    """)
    List<InventoryEntity> findLowStockByInventory();

    @Query("""
    select p
    from InventoryEntity i
    join i.productVariant pv
    join pv.product p
    group by p.id, p.name, p.minStock
    having sum(i.currentStock) <= p.minStock
    """)
    List<ProductEntity> findProductsLowStock();
}

