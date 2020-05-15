package ru.nanaslav.guitarshop.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.model.IModel;
import ru.nanaslav.guitarshop.model.*;
import ru.nanaslav.guitarshop.repository.CartItemRepository;
import ru.nanaslav.guitarshop.repository.CartRepository;
import ru.nanaslav.guitarshop.repository.DeliveryRepository;
import ru.nanaslav.guitarshop.repository.ProductRepository;

import javax.servlet.http.HttpServletRequest;

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
    public String cart(Model model,
                       @AuthenticationPrincipal User user,
                       @RequestParam(value = "orderSuccess", defaultValue = "false") boolean orderSuccess) {
        Cart cart = cartRepository.findByUser(user);
        model.addAttribute("items", cart.getItems());
        model.addAttribute("total", cart.getTotal());
        if (orderSuccess) {
            model.addAttribute("color", "w3-green");
            model.addAttribute("title", "Success");
            model.addAttribute("message", "Thank you for your order!");
        }
        model.addAttribute("orderSuccess", orderSuccess);
        return "customer/cart";
    }

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable(value = "id") long id,
                            @AuthenticationPrincipal User user,
                            Model model) {
        Product product = productRepository.findById(id).orElseThrow(IllegalStateException::new);
        if (user != null && user.getAuthorities().contains(UserRole.USER)) {
            Cart cart = cartRepository.findByUser(user);
            cart.addProduct(cartItemRepository, product);
            model.addAttribute("inCart", true);
        } else {
            model.addAttribute("inCart", false);
        }
        return "redirect:/products/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable(value = "id") long id) {
        cartItemRepository.deleteById(id);
        return "redirect:/cart";
    }

    @GetMapping("/clear")
    public String clearCart(@AuthenticationPrincipal User user,
                            @RequestParam(value = "orderSuccess", defaultValue = "false") boolean orderSuccess) {
        Cart cart = cartRepository.findByUser(user);
        cartItemRepository.deleteAll(cartItemRepository.findAllByCart(cart));
        if (orderSuccess) {
            return "redirect:/cart?orderSuccess=true";
        }
        return "redirect:/cart";
    }

    @PostMapping("/update/{item}")
    public String updateCar(@PathVariable(value = "item") long itemId,
                            @RequestParam int quantity) {
        CartItem item = cartItemRepository.findById(itemId).orElseThrow(IllegalStateException::new);
        item.setQuantity(quantity);
        cartItemRepository.save(item);
        return "redirect:/cart";
    }
}
