package ru.nanaslav.guitarshop.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nanaslav.guitarshop.model.Category;
import ru.nanaslav.guitarshop.model.Product;
import ru.nanaslav.guitarshop.repository.ProductRepository;
import ru.nanaslav.guitarshop.repository.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;

    @GetMapping("/electric")
    public String electricGuitars(Model model) {
        String title = "Electric guitars";
        model.addAttribute("title", title);
        List<Product> products = productRepository.findAllByCategoriesContains(Category.ELECTRIC);
        model.addAttribute("products", products);
        return "product-list";
    }

    @GetMapping("/acoustic")
    public String acousticGuitars(Model model) {
        String title = "Acoustic guitars";
        model.addAttribute("title", title);
        List<Product> products = productRepository.findAllByCategoriesContains(Category.ACOUSTIC);
        model.addAttribute("products", products);
        return "product-list";

    }

    @GetMapping("/bass")
    public String bassGuitars(Model model) {
        String title = "Bass guitars";
        model.addAttribute("title", title);
        List<Product> products = productRepository.findAllByCategoriesContains(Category.BASS);
        model.addAttribute("products", products);
        return "product-list";
    }

    @GetMapping("/accessories")
    public String accessories(Model model) {
        String title = "Accessories";
        model.addAttribute("title", title);
        List<Product> products = productRepository.findAllByCategoriesContains(Category.ACCESSORY);
        model.addAttribute("products", products);
        return "product-list";
    }

    @GetMapping("/amplifiers")
    public String amplifiers(Model model) {
        String title = "Amplifiers";
        model.addAttribute("title", title);
        List<Product> products = productRepository.findAllByCategoriesContains(Category.AMP);
        model.addAttribute("products", products);
        return "product-list";
    }
}
