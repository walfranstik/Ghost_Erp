package com.ghost.erp.modules.inventory.api.product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ghost.erp.modules.inventory.application.product.AttributeService;
import com.ghost.erp.modules.inventory.domain.model.product.AttributeEntity;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/attributes")
@RequiredArgsConstructor
public class AttributeController {

    private final AttributeService attributeService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("attributes", attributeService.findAll());
        return "attributes/list";
    }

    @PostMapping
    public String create(@ModelAttribute AttributeEntity attribute) {
        attributeService.create(attribute);
        return "redirect:/attributes";
    }
}
