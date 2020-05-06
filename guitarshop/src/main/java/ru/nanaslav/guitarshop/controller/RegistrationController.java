package ru.nanaslav.guitarshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nanaslav.guitarshop.model.User;
import ru.nanaslav.guitarshop.model.UserRole;
import ru.nanaslav.guitarshop.repository.UserRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Controller
public class RegistrationController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @PostMapping("registration")
    public String addUser(@AuthenticationPrincipal User currentUser,
                          User user,
                          @RequestParam String passwordCheck,
                          Model model) {
        if(!user.getPassword().equals(passwordCheck)) {
            // passwords do not match
            return "test";
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            // user exist
            return "test";
        }
        user.setActive(true);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if (currentUser != null && currentUser.getAuthorities().contains(UserRole.ADMIN)) {
            Set<UserRole> roles = new HashSet<>();
            roles.add(UserRole.USER);
            roles.add(UserRole.ADMIN);
            user.setRoles(roles);
        } else {
            user.setRoles(Collections.singleton(UserRole.USER));
        }
        userRepository.save(user);
        return "redirect:/login";

    }
}
