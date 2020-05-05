package ru.nanaslav.guitarshop.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nanaslav.guitarshop.model.Category;
import ru.nanaslav.guitarshop.model.Product;
import ru.nanaslav.guitarshop.repository.ProductRepository;
import ru.nanaslav.guitarshop.repository.UserRepository;

import java.util.ArrayList;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;

    private void setPages(int page, int last, int size, Model model) {
        ArrayList<Integer> p = new ArrayList<>();
        if (page != 1) {
            p.add(1);
            if ((page - 3) > 2) {
                p.add(0);
                p.add(page - 3);
                p.add(page - 2);
                p.add(page - 1);
                p.add(page);
            } else {
                for(int cur = 2; cur <= page; cur++) {
                    p.add(cur);
                }
            }
        } else {
            p.add(1);
        }
        if (page != last) {
            if ((page + 1) <= last) {
                p.add(page + 1);
                if ((page + 2) <= last) {
                    p.add(page + 2);
                    if ((page + 3) <= last) {
                        p.add(page + 3);
                        if ((page + 3) < last) {
                            if ((page + 4) < last ) {
                                p.add(0);
                            }
                            p.add(last);
                        }
                    }
                }
            }
        }
        int prev = (page != 1) ? page - 1 : 1;
        int next = (page != last) ? page + 1 : last;
        model.addAttribute("size", size);
        model.addAttribute("prev", prev);
        model.addAttribute("next", next);
        model.addAttribute("pageable", p);
    }
    @GetMapping("/electric")
    public String electricGuitars(@RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size,
                                  Model model) {
        String title = "Electric guitars";
        model.addAttribute("title", title);
        PageRequest request = PageRequest.of(page - 1, size);
        Page<Product> products = productRepository.findAllByCategoriesContains(Category.ELECTRIC, request);
        int last = products.getTotalPages();
        setPages(page, last, size, model);
        model.addAttribute("urlBegin", "/products/electric/");
        model.addAttribute("products", products);
        return "customer/product-list";
    }

    @GetMapping("/acoustic")
    public String acousticGuitars(@RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size,
                                  Model model) {
        String title = "Acoustic guitars";
        model.addAttribute("title", title);
        PageRequest request = PageRequest.of(page - 1, size);
        Page<Product> products = productRepository.findAllByCategoriesContains(Category.ACOUSTIC, request);
        int last = products.getTotalPages();
        setPages(page,last,size,model);
        model.addAttribute("urlBegin", "/products/electric");
        model.addAttribute("products", products);
        return "customer/product-list";

    }

    @GetMapping("/bass")
    public String bassGuitars(@RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size,
                              Model model) {
        String title = "Bass guitars";
        model.addAttribute("title", title);
        model.addAttribute("title", title);
        PageRequest request = PageRequest.of(page - 1, size);
        Page<Product> products = productRepository.findAllByCategoriesContains(Category.BASS, request);
        int last = products.getTotalPages();
        setPages(page, last, size, model);
        model.addAttribute("urlBegin", "/products/bass/");
        model.addAttribute("products", products);
        return "customer/product-list";
    }

    @GetMapping("/accessories")
    public String accessories(@RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size,
                              Model model) {
        String title = "Accessories";
        model.addAttribute("title", title);
        model.addAttribute("title", title);
        PageRequest request = PageRequest.of(page - 1, size);
        Page<Product> products = productRepository.findAllByCategoriesContains(Category.ACCESSORY, request);
        int last = products.getTotalPages();
        setPages(page, last, size, model);
        model.addAttribute("urlBegin", "/products/accessories/");
        model.addAttribute("products", products);
        return "customer/product-list";
    }

    @GetMapping("/amplifiers")
    public String amplifiers(@RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "size", defaultValue = "10") int size,
                             Model model) {
        String title = "Amplifiers";
        model.addAttribute("title", title);
        model.addAttribute("title", title);
        PageRequest request = PageRequest.of(page - 1, size);
        Page<Product> products = productRepository.findAllByCategoriesContains(Category.AMP, request);
        int last = products.getTotalPages();
        setPages(page, last, size, model);
        model.addAttribute("urlBegin", "/products/amplifiers/");
        model.addAttribute("products", products);
        return "customer/product-list";
    }

    @GetMapping("{id}")
    public String productDetails(@PathVariable(value = "id") long id,
                                 Model model) {
        Product product = productRepository.findById(id).orElseThrow(IllegalStateException::new);
        model.addAttribute("characteristics", product.getCharacteristics().split("\n"));

        model.addAttribute("product", product);
        return "customer/product-details";
    }
}
