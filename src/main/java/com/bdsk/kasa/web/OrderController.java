package com.bdsk.kasa.web;

import com.bdsk.kasa.domain.ConfirmedOrder;
import com.bdsk.kasa.domain.ShoppingCart;
import com.bdsk.kasa.repository.ConfirmedOrderRepository;
import com.bdsk.kasa.repository.ProductRepository;
import com.bdsk.kasa.service.ShoppingCartService;
import com.bdsk.kasa.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class OrderController {

    private final ShoppingCartService shoppingCartService;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final ConfirmedOrderRepository confirmedOrderRepository;

    @Autowired
    public OrderController(ShoppingCartService shoppingCartService,
                           ProductRepository productRepository,
                           UserService userService,
                           ConfirmedOrderRepository confirmedOrderRepository) {
        this.shoppingCartService = shoppingCartService;
        this.productRepository = productRepository;
        this.userService = userService;
        this.confirmedOrderRepository = confirmedOrderRepository;
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

    @PostMapping("/confirm-order")
    public String confirmOrder(HttpServletRequest request, HttpServletResponse response, Model model) {
        ShoppingCart cart = shoppingCartService.loadShoppingCartFromCookie(request);
        if (cart.getProducts().isEmpty()) {
            model.addAttribute("error", "Неможливо підтвердити: кошик - пустий!");
            return "redirect:/cart";
        }

        ConfirmedOrder confirmedOrder = new ConfirmedOrder();
        confirmedOrder.setProducts(cart.getProducts());
        confirmedOrder.setPrice(cart.getPrice());

        confirmedOrder.setAuthorId(userService.getCurrentUser().getId());

        confirmedOrderRepository.save(confirmedOrder);
        shoppingCartService.clearShoppingCart(response);

        return "redirect:/order/" + confirmedOrder.getId();
    }

    @GetMapping("/order/{id}")
    public String showProductPage(@PathVariable int id, Model model) {
        Optional<ConfirmedOrder> possiblyConfirmedOrder = confirmedOrderRepository.findById(id);
        if (possiblyConfirmedOrder.isEmpty()) {
            return "redirect:/";
        }
        ConfirmedOrder confirmedOrder = possiblyConfirmedOrder.get();
        model.addAttribute("order",  confirmedOrder);
        return "order";
    }

}
