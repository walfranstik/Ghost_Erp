package com.ghost.erp.modules.inventory.application.product;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghost.erp.modules.inventory.domain.model.product.AttributeEntity;
import com.ghost.erp.modules.inventory.domain.repository.AttributeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AttributeService {

    private final AttributeRepository attributeRepository;

    public AttributeEntity create(AttributeEntity attribute) {

        attributeRepository.findByNameIgnoreCase(attribute.getName())
            .ifPresent(a -> {
                throw new RuntimeException("Attribute already exists");
            });

        return attributeRepository.save(attribute);
    }

    @Transactional(readOnly = true)
    public List<AttributeEntity> findAll() {
        return attributeRepository.findAll();
    }
}
