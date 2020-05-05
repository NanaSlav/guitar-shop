package ru.nanaslav.guitarshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @PostMapping("/password")
    public String editPassword(@AuthenticationPrincipal User user,
                               @RequestParam String currentPassword,
                               @RequestParam String password,
                               @RequestParam String passwordCheck) {
        if (bCryptPasswordEncoder.matches(currentPassword, user.getPassword())) {
            if (password.equals(passwordCheck)) {
                user.setPassword(bCryptPasswordEncoder.encode(password));
                userRepository.save(user);
                // ok
            } else {
                // passwords do not match
            }
        } else {
            // invalid current password
        }
        return "redirect:/account";
    }

    @PostMapping("/delete")
    public String deleteAccount(@AuthenticationPrincipal User user) {
        user.setActive(false);
        user.setEmail(null);
        userRepository.save(user);
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        return "redirect:/";
    }
}
