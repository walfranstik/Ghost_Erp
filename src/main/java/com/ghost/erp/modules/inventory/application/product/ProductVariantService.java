package com.ghost.erp.modules.inventory.application.product;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghost.erp.modules.inventory.domain.model.product.ProductVariantEntity;
import com.ghost.erp.modules.inventory.domain.repository.ProductVariantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ProductVariantService {

    private final ProductVariantRepository variantRepository;

    public ProductVariantEntity create(ProductVariantEntity variant) {

        if (variantRepository.existsBySku(variant.getSku())) {
            throw new RuntimeException("SKU already exists");
        }

        return variantRepository.save(variant);
    }

    @Transactional(readOnly = true)
    public List<ProductVariantEntity> findByProduct(Long productId) {
        return variantRepository.findByProductId(productId);
    }

    public void delete(Long id) {
        variantRepository.deleteById(id);
    }
}

