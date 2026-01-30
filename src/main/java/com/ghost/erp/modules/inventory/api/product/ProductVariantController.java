package com.ghost.erp.modules.inventory.api.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ghost.erp.modules.inventory.application.product.ProductVariantService;
import com.ghost.erp.modules.inventory.domain.model.product.ProductVariantEntity;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/variants")
@RequiredArgsConstructor
public class ProductVariantController {

    private final ProductVariantService variantService;

    @PostMapping
    public String create(@ModelAttribute ProductVariantEntity variant) {
        variantService.create(variant);
        return "redirect:/products/" + variant.getProduct().getId() + "/edit";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
                         @RequestParam Long productId) {
        variantService.delete(id);
        return "redirect:/products/" + productId + "/edit";
    }
}

