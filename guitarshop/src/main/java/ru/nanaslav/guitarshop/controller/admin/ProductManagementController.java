package ru.nanaslav.guitarshop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.nanaslav.guitarshop.Pagination;
import ru.nanaslav.guitarshop.model.Category;
import ru.nanaslav.guitarshop.model.Product;
import ru.nanaslav.guitarshop.repository.ProductRepository;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
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
    public String addProductForm() {
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
}
