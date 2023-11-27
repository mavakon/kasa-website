package com.bdsk.kasa.web;

import com.bdsk.kasa.domain.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "home";

    }

     @GetMapping("/product")
    public String showProduct(Model model) {
        Product product = new Product();
        product.setName("Sample Product");
        product.setDescription("This is a sample product description.");
        product.setPrice(20);
        product.setAuthor("John Doe");

        model.addAttribute("product", product);

        return "product";
    }
}
