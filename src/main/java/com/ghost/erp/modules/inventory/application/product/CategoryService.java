package com.ghost.erp.modules.inventory.application.product;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghost.erp.modules.inventory.domain.model.product.CategoryEntity;
import com.ghost.erp.modules.inventory.domain.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryEntity create(CategoryEntity category) {

        categoryRepository.findByNameIgnoreCase(category.getName())
            .ifPresent(c -> {
                throw new RuntimeException("Category already exists");
            });

        return categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryEntity> findRoots() {
        return categoryRepository.findByParentIsNull();
    }

    @Transactional(readOnly = true)
    public List<CategoryEntity> findChildren(Long parentId) {
        return categoryRepository.findByParentId(parentId);
    }
}

