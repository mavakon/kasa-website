package com.bdsk.kasa.web;

import com.bdsk.kasa.domain.ConfirmedOrder;
import com.bdsk.kasa.domain.Product;
import com.bdsk.kasa.domain.User;
import com.bdsk.kasa.repository.ConfirmedOrderRepository;
import com.bdsk.kasa.repository.ProductRepository;
import com.bdsk.kasa.repository.UserRepository;
import com.bdsk.kasa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class KasaController {

    private final ProductRepository productRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ConfirmedOrderRepository confirmedOrderRepository;

    @Autowired
    public KasaController(ProductRepository productRepository,
                          UserService userService,
                          ConfirmedOrderRepository confirmedOrderRepository,
                          UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.confirmedOrderRepository = confirmedOrderRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String products(Model model) {
        List<Product> products = productRepository.findAll();

        // Creating a map of authorId to displayName
        Map<Integer, String> authorDisplayNames = new HashMap<>();
        products.forEach(product -> {
            authorDisplayNames.computeIfAbsent(product.getAuthorId(), authorId -> {
                User author = userRepository.findById(authorId).orElse(null);
                return author != null ? author.getDisplayName() : "Unknown";
            });
        });

        model.addAttribute("productList", products);
        model.addAttribute("authorDisplayNames", authorDisplayNames);
        return "home";
    }

    @GetMapping("/register")
    public String showProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView processProduct(Product product) {
        product.setAuthorId(userService.getCurrentUser().getId());
        if (productRepository.findByName(product.getName()).isPresent()) {
            return new ModelAndView("register", "error", "Вже є товар з такою назвою.");
        }
        productRepository.save(product);
        return new ModelAndView("register", "success", "Товар успішно додано!");
    }

    @GetMapping("/profile")
    public String profile(Model model){
        User currentUser = userService.getCurrentUser();
        int userId = currentUser.getId();
        List<Product> userProducts = productRepository.findAll().stream()
                .filter(product -> product.getAuthorId() == userId)
                .toList();
        List<ConfirmedOrder> userOrders = confirmedOrderRepository.findAll().stream()
                .filter(order -> order.getAuthorId() == userId)
                .toList();
        model.addAttribute("user", currentUser);
        model.addAttribute("productList", userProducts);
        model.addAttribute("orderList", userOrders);
        return "profile";
    }

    @GetMapping("/product/{id}")
    public String showProductPage(@PathVariable int id, Model model) {
        Optional<Product> possiblyProduct = productRepository.findById(id);
        if (possiblyProduct.isEmpty()) {
            return "redirect:/";
        }
        Product product = possiblyProduct.get();
        model.addAttribute("product", product);
        return "product";
    }


}
