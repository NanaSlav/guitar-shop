package ru.nanaslav.guitarshop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nanaslav.guitarshop.Pagination;
import ru.nanaslav.guitarshop.model.Order;
import ru.nanaslav.guitarshop.repository.OrderRepository;

@Controller
@RequestMapping("/admin/orders")
public class OrdersManagementController {
    @Autowired
    OrderRepository orderRepository;

    @GetMapping
    public String orders(@RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "75") int size,
                         @RequestParam(value = "notExecutedOnly", defaultValue = "false") boolean notExecutedOnly,
                         Model model) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<Order> orders;
        if (notExecutedOnly) {
            orders= orderRepository.findAllByExecutedFalse(request);
        } else {
            orders = orderRepository.findAll(request);
        }
        model.addAttribute("orders", orders);
        Pagination.setPages(page, orders.getTotalPages(), size, model);
        model.addAttribute("urlBegin", "/admin/orders");
        return "admin/orders";
    }

    @GetMapping("/{id}")
    public String orderDetails(@PathVariable(value = "id") long id,
                               Model model) {
        Order order = orderRepository.findById(id).orElseThrow(IllegalStateException::new);
        model.addAttribute("customer", order.getCustomer());
        model.addAttribute("order", order);
        model.addAttribute("items", order.getProducts());
        return "admin/order-details";
    }

    @PostMapping("/{id}")
    public String orderExecute(@PathVariable(value = "id") long id) {
        Order order = orderRepository.findById(id).orElseThrow(IllegalStateException::new);
        order.setExecuted(true);
        orderRepository.save(order);
        return "redirect:/admin/orders";
    }
}
