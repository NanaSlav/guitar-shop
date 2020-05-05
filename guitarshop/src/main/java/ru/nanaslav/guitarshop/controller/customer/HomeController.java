package ru.nanaslav.guitarshop.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.nanaslav.guitarshop.repository.ProductRepository;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "customer/home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("login", true);
        return "customer/home";
    }
    // TODO: remove it later. It's just temporary logout
    @GetMapping("/logout")
    public String logout() {
        return "logout-temp";
    }

    // TODO: remove it later. It's login test
    @Autowired
    ProductRepository productRepository;

    @GetMapping("/test")
    public String test() { return "test"; }
}
