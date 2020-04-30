package ru.nanaslav.guitarshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nanaslav.guitarshop.model.User;
import ru.nanaslav.guitarshop.repository.UserRepository;

@Controller
public class RegistrationController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("registration")
    public String addUser( User user,
                           // @RequestParam String passwordCheck,
                           Model model) {
        userRepository.save(user);
        return "redirect:/login";

    }
}
