package com.bdsk.kasa.web;

import com.bdsk.kasa.domain.ConfirmedOrder;
import com.bdsk.kasa.domain.Product;
import com.bdsk.kasa.domain.ShoppingCart;
import com.bdsk.kasa.repository.ConfirmedOrderRepository;
import com.bdsk.kasa.repository.ProductRepository;
import com.bdsk.kasa.service.ShoppingCartService;
import com.bdsk.kasa.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private ShoppingCartService shoppingCartService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserService userService;

    @Mock
    private ConfirmedOrderRepository confirmedOrderRepository;

    @InjectMocks
    private OrderController orderController;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShowCart() {
        Map<Product, Integer> productsMap = new HashMap<>();
        ShoppingCart cart = new ShoppingCart(productsMap);
        when(shoppingCartService.loadShoppingCartFromCookie(request)).thenReturn(cart);

        String viewName = orderController.showCart(request, model);

        assertEquals("cart", viewName);
        verify(model).addAttribute("cart", cart);
    }

    @Test
    void testAddToCart() {
        int productId = 1;
        Product product = new Product();
        product.setName("adsf");
        product.setDescription("dlmsbl");
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Map<Product, Integer> productsMap = new HashMap<>();
        ShoppingCart cart = new ShoppingCart(productsMap);
        when(shoppingCartService.loadShoppingCartFromCookie(request)).thenReturn(cart);

        String viewName = orderController.addToCart(productId, request, response);

        assertEquals("redirect:/product/{id}", viewName);
        verify(shoppingCartService).loadShoppingCartFromCookie(request);
        verify(shoppingCartService).saveShoppingCartToCookie(any(), eq(response));
    }

    @Test
    void testShowProductPage() {
        int orderId = 1;
        ConfirmedOrder confirmedOrder = new ConfirmedOrder();
        confirmedOrder.setId(orderId);
        when(confirmedOrderRepository.findById(orderId)).thenReturn(Optional.of(confirmedOrder));

        String viewName = orderController.showProductPage(orderId, model);

        assertEquals("order", viewName);
        verify(model).addAttribute("confirmedOrder", confirmedOrder);
    }

    @Test
    void testShowProductPageWithInvalidOrderId() {
        int orderId = 1;
        when(confirmedOrderRepository.findById(orderId)).thenReturn(Optional.empty());

        String viewName = orderController.showProductPage(orderId, model);

        assertEquals("redirect:/", viewName);
        verify(model, never()).addAttribute(any(), any());
    }
}
