package ru.nanaslav.guitarshop.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nanaslav.guitarshop.model.Cart;
import ru.nanaslav.guitarshop.model.User;
import ru.nanaslav.guitarshop.repository.CartItemRepository;
import ru.nanaslav.guitarshop.repository.CartRepository;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    CartRepository cartRepository;


    @GetMapping
    public String ordering(@AuthenticationPrincipal User user,
                           Model model) {
        Cart cart = cartRepository.findByUser(user);
        if (!cart.getItems().isEmpty()) {
            model.addAttribute("items", cart.getItems());
            model.addAttribute("total", cart.getTotal());
            return "customer/order";
        }
        return "redirect:/cart";
    }
}
