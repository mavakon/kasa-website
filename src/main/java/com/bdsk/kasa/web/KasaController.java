package com.bdsk.kasa.web;

import com.bdsk.kasa.domain.Product;
import com.bdsk.kasa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class KasaController {

    private final ProductRepository productRepository;


    @Autowired
    public KasaController(ProductRepository productRepository) {
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
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView processProduct(Product product) {
        if (productRepository.findByName(product.getName()).isPresent()) {
            return new ModelAndView("register", "error", "Вже є товар з такою назвою.");
        }
        productRepository.save(product);
        return new ModelAndView("register", "success", "Товар успішно додано!");
    }

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


}
