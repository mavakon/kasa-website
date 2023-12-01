package com.bdsk.kasa.web;

import com.bdsk.kasa.domain.Product;
import com.bdsk.kasa.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/kasa")
public class ProdController {
    private final ProductRepository productRepository = new ProductRepository();

    @GetMapping("/")
    public String products(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "home";
    }

    @GetMapping("/register")
    public String showProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "prod";
    }

    @GetMapping("/cart")
    public String cart(){ return "cart";}

    @GetMapping("/profile")
    public String profile(){ return "profile";}

    @PostMapping("/process")
    public String processProduct(Product product) {
        productRepository.save(product);
        return "redirect:/kasa/";
    }
}
