package com.ghost.erp.modules.inventory.application.product;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghost.erp.modules.inventory.domain.model.product.AttributeOptionEntity;
import com.ghost.erp.modules.inventory.domain.repository.AttributeOptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AttributeOptionService {

    private final AttributeOptionRepository optionRepository;

    public AttributeOptionEntity create(AttributeOptionEntity option) {
        return optionRepository.save(option);
    }

    @Transactional(readOnly = true)
    public List<AttributeOptionEntity> findByAttribute(Long attributeId) {
        return optionRepository.findByAttributeId(attributeId);
    }
}
