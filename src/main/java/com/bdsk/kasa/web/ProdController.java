package com.bdsk.kasa.web;

import com.bdsk.kasa.domain.Product;
import com.bdsk.kasa.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProdController {
    private final ProductRepository productRepository = new ProductRepository();

    @GetMapping("/")
    public String products(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "home";
    }

    @GetMapping("/register")
    public String showProductForm() {
        return "prod";
    }

    @PostMapping("/process")
    public String processProduct(Product product) {
        productRepository.save(product);
        return "redirect:/products/";
    }
}
