package com.ghost.erp.modules.inventory.api.product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ghost.erp.modules.inventory.application.product.AttributeOptionService;
import com.ghost.erp.modules.inventory.domain.model.product.AttributeOptionEntity;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/attribute-options")
@RequiredArgsConstructor

public class AttributeOptionController {

    private final AttributeOptionService optionService;

    /* ========================
       CREATE OPTION
    ========================= */

    @PostMapping
    public String create(@ModelAttribute AttributeOptionEntity option) {

        optionService.create(option);

        return "redirect:/attributes";
    }

    /* ========================
       LIST BY ATTRIBUTE (HTMX)
    ========================= */

    @GetMapping("/by-attribute/{attributeId}")
    public String listByAttribute(@PathVariable Long attributeId,
                                  Model model) {

        model.addAttribute(
            "options",
            optionService.findByAttribute(attributeId)
        );

        return "attribute-options/fragments/list";
    }
}
