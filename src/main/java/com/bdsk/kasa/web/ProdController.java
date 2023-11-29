package com.bdsk.kasa.web;

import com.bdsk.kasa.domain.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
public class ProdController {

    @GetMapping("/register")
    public String showProductForm() {
        return "prod";
    }

    @PostMapping("/process")
    public String processProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            Model model) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        // код для обробки даних
        System.out.println("Received product data - Name: " + name + ", Description: " + description + ", Price: " + price);

        return "redirect:/";
    }
}
