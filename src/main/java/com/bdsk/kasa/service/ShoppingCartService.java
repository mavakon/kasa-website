package com.bdsk.kasa.service;

import com.bdsk.kasa.domain.Product;
import com.bdsk.kasa.domain.ShoppingCart;
import com.bdsk.kasa.repository.ProductRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ShoppingCartService {

    private static final Gson gson = new Gson();
    private final ProductRepository productRepository;

    @Autowired
    public ShoppingCartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ShoppingCart loadShoppingCartFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("shoppingCart".equals(cookie.getName())) {
                    return deserializeShoppingCart(URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8));
                }
            }
        }
        return new ShoppingCart(new HashMap<>());
    }

    public void saveShoppingCartToCookie(ShoppingCart shoppingCart, HttpServletResponse response) {
        String json = serializeShoppingCart(shoppingCart);
        Cookie cookie = new Cookie("shoppingCart", URLEncoder.encode(json, StandardCharsets.UTF_8));
        cookie.setMaxAge(60 * 60 * 24 * 7);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public void clearShoppingCart(HttpServletResponse response) {
        ShoppingCart emptyCart = new ShoppingCart(new HashMap<>());
        saveShoppingCartToCookie(emptyCart, response);
    }

    public String serializeShoppingCart(ShoppingCart shoppingCart) {
        Map<Integer, Integer> cartMap = new HashMap<>();
        shoppingCart.getProducts().forEach((product, quantity) -> cartMap.put(product.getId(), quantity));
        return gson.toJson(cartMap);
    }

    public ShoppingCart deserializeShoppingCart(String json) {
        Type type = new TypeToken<Map<Integer, Integer>>() {
        }.getType();
        Map<Integer, Integer> cartMap = gson.fromJson(json, type);
        Map<Product, Integer> products = new HashMap<>();
        cartMap.forEach((productId, quantity) -> {
            Optional<Product> possiblyProduct = productRepository.findById(productId);
            possiblyProduct.ifPresent(product -> products.put(product, quantity));
        });
        return new ShoppingCart(products);
    }

}
