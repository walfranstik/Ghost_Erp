package com.ghost.erp.modules.inventory.application.product;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghost.erp.modules.inventory.domain.model.product.ProductEntity;
import com.ghost.erp.modules.inventory.domain.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private final ProductRepository productRepository;

    /* CREATE */
    public ProductEntity create(ProductEntity product) {

        if (productRepository.existsByCode(product.getCode())) {
            throw new RuntimeException("Product code already exists");
        }

        return productRepository.save(product);
    }

    /* READ */
    @Transactional(readOnly = true)
    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ProductEntity findById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    /* UPDATE */
    public ProductEntity update(Long id, ProductEntity data) {

        ProductEntity product = findById(id);

        product.setName(data.getName());
        product.setDescription(data.getDescription());
        product.setMinStock(data.getMinStock());
        product.setMaxStock(data.getMaxStock());
        product.setCategory(data.getCategory());
        product.setBrand(data.getBrand());

        return productRepository.save(product);
    }

    /* DELETE */
    public void delete(Long id) {
        productRepository.delete(findById(id));
    }
}

