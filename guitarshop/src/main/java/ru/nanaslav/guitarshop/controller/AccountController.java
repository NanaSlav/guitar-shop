package ru.nanaslav.guitarshop.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.nanaslav.guitarshop.model.User;
import ru.nanaslav.guitarshop.model.UserRole;

@Controller
public class AccountController {
    @GetMapping("/account")
    public String accountPage(@AuthenticationPrincipal User user,
                              Model model) {
        model.addAttribute("user", user);
        if (user.getAuthorities().contains(UserRole.ADMIN)) {
            return "admin/account";
        }

        return "customer/account";

    }
}
