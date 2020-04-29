package ru.nanaslav.guitarshop.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {
    @GetMapping("/electric")
    public String electricGuitars() {
        return "product-list";
    }

    @GetMapping("/acoustic")
    public String acousticGuitars() {
        return "product-list";
    }

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
