package com.ghost.erp.modules.inventory.api.product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ghost.erp.modules.inventory.application.product.CategoryService;
import com.ghost.erp.modules.inventory.domain.model.product.CategoryEntity;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor

public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", categoryService.findRoots());
        return "categories/list";
    }

    @PostMapping
    public String create(@ModelAttribute CategoryEntity category) {
        categoryService.create(category);
        return "redirect:/categories";
    }
}
