package com.ghost.erp.modules.finance.api.dto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ghost.erp.modules.finance.application.CashMovementService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/cash")
@RequiredArgsConstructor

public class CashMovementController {

    private final CashMovementService cashService;

    @GetMapping
    public String dashboard(Model model) {

        model.addAttribute("balance", cashService.getBalance());

        return "cash/dashboard";
    }
}
