package ru.nanaslav.guitarshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nanaslav.guitarshop.model.User;
import ru.nanaslav.guitarshop.model.UserRole;
import ru.nanaslav.guitarshop.repository.UserRepository;

import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    UserRepository userRepository;


    @PostMapping("registration")
    public String addUser( User user,
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
        user.setRoles(Collections.singleton(UserRole.USER));
        userRepository.save(user);
        return "redirect:/login";

    }
}
