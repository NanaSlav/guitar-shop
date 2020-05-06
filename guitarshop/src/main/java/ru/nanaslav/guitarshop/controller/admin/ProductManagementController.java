package ru.nanaslav.guitarshop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
