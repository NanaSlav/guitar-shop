package ru.nanaslav.guitarshop.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nanaslav.guitarshop.model.*;
import ru.nanaslav.guitarshop.repository.CartItemRepository;
import ru.nanaslav.guitarshop.repository.CartRepository;
import ru.nanaslav.guitarshop.repository.ProductRepository;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartItemRepository cartItemRepository;


    @GetMapping
    public String cart() {
        return "customer/cart";
    }

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable(value = "id") long id,
                            @AuthenticationPrincipal User user) {
        Product product = productRepository.findById(id).orElseThrow(IllegalStateException::new);
        if (user != null && user.getAuthorities().contains(UserRole.USER)) {
            Cart cart = cartRepository.findByUser(user);
            cart.addProduct(cartItemRepository, product);
        }
        return "redirect:/products/" + id;
    }
}
