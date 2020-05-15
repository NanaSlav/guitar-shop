package ru.nanaslav.guitarshop.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nanaslav.guitarshop.model.*;
import ru.nanaslav.guitarshop.repository.*;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    OrderedProductRepository orderedProductRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    OrderRepository orderRepository;




    @GetMapping
    public String ordering(@AuthenticationPrincipal User user,
                           Model model) {
        Cart cart = cartRepository.findByUser(user);
        if (!cart.getItems().isEmpty()) {
            model.addAttribute("items", cart.getItems());
            model.addAttribute("total", cart.getTotal());
            model.addAttribute("shops", deliveryRepository.findAllByDeliveryType(DeliveryType.PICKUP));
            return "customer/order";
        }
        return "redirect:/cart";
    }

    @PostMapping
    public String order(@RequestParam(name = "delivery") String deliveryType,
                        @RequestParam(name = "address", defaultValue = "") String address,
                        @RequestParam(name = "shopId", defaultValue = "0") long shopId,
                        @AuthenticationPrincipal User user,
                        Model model) {
        Delivery delivery;
        if (deliveryType.equals("delivery")) {
            delivery = new Delivery();
            delivery.setDefaultPrice();
            delivery.setAddress(address);
            delivery.setName("Delivery");
            delivery.setDeliveryType(DeliveryType.DELIVERY);
            deliveryRepository.save(delivery);
        } else {
            delivery = deliveryRepository.findById(shopId).orElseThrow(IllegalStateException::new);
        }

        Order order = new Order();
        order.setCustomer(user);
        order.setDelivery(delivery);
        order.setExecuted(false);
        order.setDate(Date.valueOf(LocalDate.now()));
        orderRepository.save(order);

        Cart cart = cartRepository.findByUser(user);
        for (CartItem item : cart.getItems()) {
            OrderedProduct orderedProduct = new OrderedProduct();
            orderedProduct.setOrder(order);
            orderedProduct.setQuantity(item.getQuantity());
            orderedProduct.setProduct(item.getProduct());
            orderedProductRepository.save(orderedProduct);
        }
        return "redirect:/cart/clear?orderSuccess=true";
    }
}
