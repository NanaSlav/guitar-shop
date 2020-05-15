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
import ru.nanaslav.guitarshop.model.Order;
import ru.nanaslav.guitarshop.model.User;
import ru.nanaslav.guitarshop.model.UserRole;
import ru.nanaslav.guitarshop.repository.OrderRepository;
import ru.nanaslav.guitarshop.repository.UserRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping
    public String accountPage(@AuthenticationPrincipal User user,
                              Model model) {
        model.addAttribute("user", user);
        if (user.getAuthorities().contains(UserRole.ADMIN)) {
            return "admin/account";
        }

        List<Order> orders = orderRepository.findAllByCustomer(user);
        Collections.reverse(orders);

        if (orders.isEmpty()) {
            model.addAttribute("emptyHistory", true);
        } else {
            model.addAttribute("emptyHistory", false);
            model.addAttribute("orders", orders);
        }

        return "customer/account";

    }

    @PostMapping("/edit")
    public String editAccount(@AuthenticationPrincipal User currentUser,
                              @RequestParam String email,
                              @RequestParam String name,
                              @RequestParam String surname,
                              @RequestParam String phone,
                              @RequestParam String dateOfBirth,
                              Model model) {
        User user = userRepository.findByEmail(currentUser.getEmail());
        model.addAttribute("hasMessage", true);
        if (!currentUser.getEmail().equals(email) && userRepository.findByEmail(email) != null) {
            // User with such email already exist
            model.addAttribute("title", "Error");
            model.addAttribute("color", "w3-red");
            model.addAttribute("message", "User with such email already exists");
            return accountPage(user, model);
        }
        user.setEmail(email);
        user.setName(name);
        user.setSurname(surname);
        user.setPhone(phone);
        if (!dateOfBirth.equals("")) {
            Date date = Date.valueOf(dateOfBirth);
            user.setDateOfBirth(date);
        }
        userRepository.save(user);
        model.addAttribute("color", "w3-green");
        model.addAttribute("title", "Success");
        model.addAttribute("message", "Account information is successfully changed");
        return accountPage(user, model);
    }

    @PostMapping("/password")
    public String editPassword(@AuthenticationPrincipal User user,
                               @RequestParam String currentPassword,
                               @RequestParam String password,
                               @RequestParam String passwordCheck,
                               Model model) {
        model.addAttribute("hasMessage", true);
        if (bCryptPasswordEncoder.matches(currentPassword, user.getPassword())) {
            if (password.equals(passwordCheck)) {
                user.setPassword(bCryptPasswordEncoder.encode(password));
                userRepository.save(user);
                // ok
                model.addAttribute("color", "w3-green");
                model.addAttribute("title", "Success");
                model.addAttribute("message", "Password successfully changed");
            } else {
                // Password do not match
                model.addAttribute("color", "w3-red");
                model.addAttribute("title", "Error");
                model.addAttribute("message", "Passwords do not match");
            }
        } else {
            // invalid current password
            model.addAttribute("color", "w3-red");
            model.addAttribute("title", "Error");
            model.addAttribute("message", "Invalid password");
        }
        if (user.getAuthorities().contains(UserRole.ADMIN)) {
            return "admin/account";
        }
        return accountPage(user, model);
    }

    @PostMapping("/delete")
    public String deleteAccount(@AuthenticationPrincipal User user, Model model) {
        user.setActive(false);
        user.setEmail(null);
        userRepository.save(user);
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        return "redirect:/home";
    }
}
