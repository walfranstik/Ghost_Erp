package com.ghost.erp.modules.users.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ghost.erp.modules.users.application.UserService;
import com.ghost.erp.modules.users.domain.model.UserEntity;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    /* ========================
       PAGE
    ======================== */

    @GetMapping
    public String page(Model model) {

        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", UserEntity.Role.values());

        return "users/index";
    }

    /* ========================
       CREATE
    ======================== */

    @PostMapping
    public String create(@RequestParam String name,
                         @RequestParam String email,
                         @RequestParam String password,
                         @RequestParam UserEntity.Role role) {

        userService.create(name, email, password, role);

        return "redirect:/users";
    }

    /* ========================
       UPDATE
    ======================== */

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id,
                         @RequestParam String name,
                         @RequestParam String email,
                         @RequestParam UserEntity.Role role) {

        userService.update(id, name, email, role);

        return "redirect:/users";
    }

    /* ========================
       CHANGE PASSWORD
    ======================== */

    @PostMapping("/{id}/password")
    public String changePassword(@PathVariable Long id,
                                 @RequestParam String password) {

        userService.changePassword(id, password);

        return "redirect:/users";
    }

    /* ========================
       DELETE
    ======================== */

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {

        userService.delete(id);

        return "redirect:/users";
    }
}

