package com.bdsk.kasa.web;

import com.bdsk.kasa.domain.Product;
import com.bdsk.kasa.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/kasa")
public class ProdController {

    private final ProductRepository productRepository;

    public ProdController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    public String products(Model model) {
        model.addAttribute("productList", productRepository.findAll());
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

    @GetMapping("/product/{id}")
    public String showProductPage(@PathVariable int id, Model model) {
        Optional<Product> possiblyProduct = productRepository.findById(id);
        if (possiblyProduct.isEmpty()) {
            return "home";
        }
        Product product = possiblyProduct.get();
        model.addAttribute("product", product);

        return "product";
    }

    @PostMapping("/process")
    public String processProduct(Product product) {
        productRepository.save(product);
        return "redirect:/kasa/";
    }
}
