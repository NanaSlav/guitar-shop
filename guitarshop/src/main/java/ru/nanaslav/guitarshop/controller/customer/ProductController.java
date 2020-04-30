package ru.nanaslav.guitarshop.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nanaslav.guitarshop.model.User;
import ru.nanaslav.guitarshop.repository.UserRepository;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/electric")
    public String electricGuitars() {
        return "product-list";
    }

    @GetMapping("/acoustic")
    public String acousticGuitars() { return "product-list"; }

    @GetMapping("/bass")
    public String bassGuitars() {
        return "product-list";
    }

    @GetMapping("/accessories")
    public String accessories() {
        return "product-list";
    }

    @GetMapping("/amplifiers")
    public String amplifiers() {
        return "product-list";
    }
}
