package ru.nanaslav.guitarshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nanaslav.guitarshop.model.User;
import ru.nanaslav.guitarshop.model.UserRole;
import ru.nanaslav.guitarshop.repository.UserRepository;

import java.sql.Date;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String accountPage(@AuthenticationPrincipal User user,
                              Model model) {
        model.addAttribute("user", user);
        if (user.getAuthorities().contains(UserRole.ADMIN)) {
            return "admin/account";
        }

        return "customer/account";

    }

    @PostMapping("/edit")
    public String editAccount(@AuthenticationPrincipal User currentUser,
                              @RequestParam String email,
                              @RequestParam String name,
                              @RequestParam String surname,
                              @RequestParam String phone,
                              @RequestParam Date dateOfBirth) {
        User user = userRepository.findByEmail(currentUser.getEmail());
        if (!currentUser.getEmail().equals(email) && userRepository.findByEmail(email) != null) {
            // User with such email already exist
            return "";
        }
        user.setEmail(email);
        user.setName(name);
        user.setSurname(surname);
        user.setPhone(phone);
        user.setDateOfBirth(dateOfBirth);
        userRepository.save(user);
        return "redirect:/account";
    }
}
