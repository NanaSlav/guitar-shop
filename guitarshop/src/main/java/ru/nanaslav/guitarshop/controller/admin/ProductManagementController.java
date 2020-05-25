package ru.nanaslav.guitarshop.controller.admin;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nanaslav.guitarshop.Pagination;
import ru.nanaslav.guitarshop.model.Category;
import ru.nanaslav.guitarshop.model.Product;
import ru.nanaslav.guitarshop.repository.ProductRepository;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("/admin/products")
public class ProductManagementController {
    @Value("${upload.path}")
    String uploadPath;

    @Autowired
    ProductRepository productRepository;

    @GetMapping()
    public String products(@RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "size", defaultValue = "75") int size,
                           @RequestParam(value = "category", defaultValue = "none") String category,
                           Model model) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<Product> products;
        switch (category) {
            case "electric":
                products = productRepository.findAllByCategoriesContains(Category.ELECTRIC, request);
                model.addAttribute("urlBegin", "/admin/products?category=electric");
                break;
            case "acoustic":
                products = productRepository.findAllByCategoriesContains(Category.ACOUSTIC, request);
                model.addAttribute("urlBegin", "/admin/products?category=acoustic");
                break;
            case "bass":
                products = productRepository.findAllByCategoriesContains(Category.BASS, request);
                model.addAttribute("urlBegin", "/admin/products?category=bass");
                break;
            case "amp":
                products = productRepository.findAllByCategoriesContains(Category.AMP, request);
                model.addAttribute("urlBegin", "/admin/products?category=amp");
                break;
            case "accessory":
                products = productRepository.findAllByCategoriesContains(Category.ACCESSORY, request);
                model.addAttribute("urlBegin", "/admin/products?category=accessory");
                break;
            default:
                products = productRepository.findAll(request);
                model.addAttribute("urlBegin", "/admin/products");

        }
        Pagination.setPages(page, products.getTotalPages(), size, model);
        model.addAttribute("products", products);
        return "admin/product-list";
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("action", "/admin/products/add");
        return "admin/add-product";
    }

    private void saveData(Model model,
                          int price,
                          String description,
                          String characteristics,
                          String producer) {
        model.addAttribute("price", price);
        model.addAttribute("description", description);
        model.addAttribute("characteristics", characteristics);
        model.addAttribute("producer", producer);
    }



    @PostMapping("add")
    public String addProduct(@RequestParam String name,
                             @RequestParam int price,
                             @RequestParam String description,
                             @RequestParam String characteristics,
                             @RequestParam String producer,
                             @RequestParam String category,
                             @RequestParam("image") MultipartFile image,
                             Model model) throws IOException {
        model.addAttribute("action", "/admin/products/add");
        if (productRepository.findByName(name) != null) {
            model.addAttribute("hasMessage", true);
            model.addAttribute("color", "w3-red");
            model.addAttribute("title", "Error");
            model.addAttribute("message", "Product with such name already exists");
            saveData(model, price, description, characteristics, producer);
            return "admin/add-product";
        }

        Product product = new Product();
        product.setName(name);
        product.setProducer(producer);

        switch (category) {
            case "electric":
                product.setCategories(Collections.singleton(Category.ELECTRIC));
                break;
            case "acoustic":
                product.setCategories(Collections.singleton(Category.ACOUSTIC));
                break;
            case "bass":
                product.setCategories(Collections.singleton(Category.BASS));
                break;
            case "amp":
                product.setCategories(Collections.singleton(Category.AMP));
                break;
            case "accessory":
                product.setCategories(Collections.singleton(Category.ACCESSORY));
                break;

        }

        String filename = "";
        if (image != null) {

            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            filename = UUID.randomUUID().toString();
            filename = filename + image.getOriginalFilename();

            image.transferTo(new File(uploadPath + "/" + filename));

        }
        product.setImage(filename);
        product.setDescription(description);
        product.setAvailable(true);
        product.setPrice(price);
        product.setCharacteristics(characteristics);
        productRepository.save(product);

        model.addAttribute("hasMessage", true);
        model.addAttribute("color", "w3-green");
        model.addAttribute("title", "Success");
        model.addAttribute("message", "Product is successfully added");

        return "admin/add-product";

    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable(value = "id") long id,
                              Model model) {
        Product product = productRepository.findById(id).orElseThrow(IllegalStateException::new);
        model.addAttribute("action", "/admin/products/edit/" + id);
        saveData(model,
                product.getPrice(),
                product.getDescription(),
                product.getCharacteristics(),
                product.getProducer());
        model.addAttribute("name", product.getName());
        Set<Category> categories = product.getCategories();
        if (categories.contains(Category.ELECTRIC)) {
            model.addAttribute("category", "electric");
        } else {
            if (categories.contains(Category.ACOUSTIC)) {
                model.addAttribute("category", "acoustic");
            } else {
                if (categories.contains(Category.BASS)) {
                    model.addAttribute("category", "bass");
                }
                 else {
                    if (categories.contains(Category.AMP)) {
                        model.addAttribute("category", "amp");
                    } else {
                        if (categories.contains(Category.ACCESSORY)) {
                            model.addAttribute("category", "accessory");
                        }
                    }
                }
            }
        }
        model.addAttribute("image", product.getImage());
        return "admin/add-product";
    }

    @PostMapping("/edit/{id}")
    public String saveEditedProduct(@PathVariable(value = "id") long id,
                                    @RequestParam String name,
                                    @RequestParam int price,
                                    @RequestParam String description,
                                    @RequestParam String characteristics,
                                    @RequestParam String producer,
                                    @RequestParam("image") MultipartFile image,
                                    Model model) throws IOException {
        Product product = productRepository.findById(id).orElseThrow(IllegalStateException::new);

        product.setName(name);
        product.setPrice(price);
        product.setProducer(producer);
        product.setCharacteristics(characteristics);
        product.setDescription(description);

        if (!image.getOriginalFilename().equals("")) {
            String filename = "";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            filename = UUID.randomUUID().toString();
            filename = filename + "." + image.getOriginalFilename();

            image.transferTo(new File(uploadPath + "/" + filename));
            product.setImage(filename);
        }

        productRepository.save(product);


        return "redirect:/admin/products";
    }
}
