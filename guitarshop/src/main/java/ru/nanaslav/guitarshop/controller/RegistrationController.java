package ru.nanaslav.guitarshop.controller;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nanaslav.guitarshop.model.Cart;
import ru.nanaslav.guitarshop.model.User;
import ru.nanaslav.guitarshop.model.UserRole;
import ru.nanaslav.guitarshop.repository.CartRepository;
import ru.nanaslav.guitarshop.repository.UserRepository;

import java.sql.Date;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Controller
public class RegistrationController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("registration", true);
        return "customer/home";
    }

    private void saveData(Model model,
                          String email,
                          String name,
                          String surname,
                          String phone,
                          String dateOfBirth) {
        model.addAttribute("email", email);
        model.addAttribute("name", name);
        model.addAttribute("surname", surname);
        model.addAttribute("phone", phone);
        if (!dateOfBirth.equals("")) {
            model.addAttribute("dateOfBirth", Date.valueOf(dateOfBirth));
        }

    }

    @PostMapping("registration")
    public String addUser(@AuthenticationPrincipal User currentUser,
                          @RequestParam String email,
                          @RequestParam String password,
                          @RequestParam String passwordCheck,
                          @RequestParam String name,
                          @RequestParam String surname,
                          @RequestParam String phone,
                          @RequestParam String dateOfBirth,
                          Model model) {
        if(!password.equals(passwordCheck)) {
            // passwords do not match
            model.addAttribute("hasMessage", true);
            model.addAttribute("color", "w3-red");
            model.addAttribute("title", "Error");
            model.addAttribute("message", "Passwords do not match");
            saveData(model, email, name, surname, phone, dateOfBirth);
            return registration(model);
        }

        if (userRepository.findByEmail(email) != null) {
            // user exist
            model.addAttribute("hasMessage", true);
            model.addAttribute("color", "w3-red");
            model.addAttribute("title", "Error");
            model.addAttribute("message", "User with such email already exit");
            saveData(model, "", name, surname, phone, dateOfBirth);
            return registration(model);
        }
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setSurname(surname);
        user.setPhone(phone);
        if (!dateOfBirth.equals("")) {
            user.setDateOfBirth(Date.valueOf(dateOfBirth));
        }
        user.setActive(true);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        if (currentUser != null && currentUser.getAuthorities().contains(UserRole.ADMIN)) {
            user.setRoles(Collections.singleton(UserRole.ADMIN));
            userRepository.save(user);
        } else {
            user.setRoles(Collections.singleton(UserRole.USER));
            Cart cart = new Cart(user);
            cart.setUser(user);
            userRepository.save(user);
            cartRepository.save(cart);
        }
        return "redirect:/login";
    }
}
