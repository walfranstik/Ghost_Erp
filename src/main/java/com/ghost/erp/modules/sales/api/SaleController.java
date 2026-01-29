package com.ghost.erp.modules.sales.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ghost.erp.modules.sales.application.SaleService;
import com.ghost.erp.modules.sales.domain.model.SaleEntity;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    /* ========================
       LIST
    ========================= */

    @GetMapping
    public String list(Model model) {
        model.addAttribute("sales", saleService.findAll());
        return "sales/list";
    }

    /* ========================
       FORM CREATE
    ========================= */

    @GetMapping("/new")
    public String formCreate(Model model) {
        model.addAttribute("sale", new SaleEntity());
        return "sales/form";
    }

    /* ========================
       CREATE
    ========================= */

    @PostMapping
    public String create(@ModelAttribute SaleEntity sale) {
        saleService.create(sale);
        return "redirect:/sales";
    }

    /* ========================
       VIEW
    ========================= */

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("sale", saleService.findById(id));
        return "sales/view";
    }

    /* ========================
       FORM UPDATE
    ========================= */

    @GetMapping("/{id}/edit")
    public String formEdit(@PathVariable Long id, Model model) {
        model.addAttribute("sale", saleService.findById(id));
        return "sales/form";
    }

    /* ========================
       UPDATE
    ========================= */

    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute SaleEntity sale
    ) {
        saleService.update(id, sale);
        return "redirect:/sales";
    }

    /* ========================
       DELETE
    ========================= */

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        saleService.delete(id);
        return "redirect:/sales";
    }

    /* ========================
       STATUS (HTMX)
    ========================= */

    @PostMapping("/{id}/status")
    public String changeStatus(
            @PathVariable Long id,
            @RequestParam SaleEntity.SaleStatus status,
            Model model
    ) {
        SaleEntity sale = saleService.changeStatus(id, status);
        model.addAttribute("sale", sale);

        // Fragmento HTMX
        return "sales/fragments/status";
    }
}

