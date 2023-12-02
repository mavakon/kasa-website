package com.bdsk.kasa.web;

import com.bdsk.kasa.domain.ShoppingCart;
import com.bdsk.kasa.repository.ProductRepository;
import com.bdsk.kasa.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CartController {

    private final ShoppingCartService shoppingCartService;
    private final ProductRepository productRepository;

    @Autowired
    public CartController(ShoppingCartService shoppingCartService, ProductRepository productRepository) {
        this.shoppingCartService = shoppingCartService;
        this.productRepository = productRepository;
    }

    @GetMapping("/cart")
    public String showCart(HttpServletRequest request, Model model) {
        ShoppingCart cart = shoppingCartService.loadShoppingCartFromCookie(request);
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {
        ShoppingCart cart = shoppingCartService.loadShoppingCartFromCookie(request);
        productRepository.findById(id).ifPresent(cart::addProduct);
        shoppingCartService.saveShoppingCartToCookie(cart, response);
        return "redirect:/product/{id}";
    }

    @PostMapping("/remove-from-cart/{id}")
    public String removeFromCart(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {
        ShoppingCart cart = shoppingCartService.loadShoppingCartFromCookie(request);
        productRepository.findById(id).ifPresent(cart::removeProduct);
        shoppingCartService.saveShoppingCartToCookie(cart, response);
        return "redirect:/cart";
    }
}
