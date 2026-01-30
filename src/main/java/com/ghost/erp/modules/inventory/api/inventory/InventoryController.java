package com.ghost.erp.modules.inventory.api.inventory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ghost.erp.modules.inventory.application.inventory.InventoryService;
import com.ghost.erp.modules.inventory.domain.model.inventory.InventoryEntity;
import com.ghost.erp.modules.inventory.domain.model.inventory.InventoryMovementEntity;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/inventory")
@RequiredArgsConstructor

public class InventoryController {

    private final InventoryService inventoryService;

    /* ALERTAS */

    @GetMapping("/alerts")
    public String alerts(Model model) {
        model.addAttribute("alerts",
            inventoryService.findLowStock()
        );
        return "inventory/alerts";
    }

    /* AJUSTE MANUAL */

    @PostMapping("/adjust")
    public String adjust(
        @RequestParam Long inventoryId,
        @RequestParam Integer quantity,
        @RequestParam InventoryMovementEntity.OperationType operation,
        @RequestParam String reason
    ) {

        InventoryEntity inventory =
            inventoryService.findById(inventoryId)
            .orElseThrow(() -> new RuntimeException("No se encontró el inventario con ID: " + inventoryId));

        inventoryService.adjustStock(
            inventory,
            quantity,
            operation,
            reason
        );

        return "redirect:/inventory/alerts";
    }
}
