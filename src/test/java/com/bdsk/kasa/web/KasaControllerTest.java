package com.bdsk.kasa.web;

import com.bdsk.kasa.domain.Product;
import com.bdsk.kasa.domain.User;
import com.bdsk.kasa.repository.ProductRepository;
import com.bdsk.kasa.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class KasaControllerTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserService userService;


    @InjectMocks
    private KasaController kasaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProducts() {
        Model model = mock(Model.class);

        String viewName = kasaController.products(model);

        assertEquals("home", viewName);
        verify(model).addAttribute("productList", productRepository.findAll());
    }

    @Test
    void testProcessProduct() {
        Product product = new Product();
        User user = new User();
        user.setId(1);
        when(userService.getCurrentUser()).thenReturn(user);
        when(productRepository.findByName(any())).thenReturn(Optional.empty());

        ModelAndView modelAndView = kasaController.processProduct(product);

        assertEquals("register", modelAndView.getViewName());
        assertEquals("Товар успішно додано!", modelAndView.getModel().get("success"));
        verify(productRepository).save(product);
    }

    @Test
    void testProcessProductWithExistingProduct() {
        Product product = new Product();
        User user = new User();
        user.setId(1);
        when(userService.getCurrentUser()).thenReturn(user);
        when(productRepository.findByName(any())).thenReturn(Optional.of(new Product()));

        ModelAndView modelAndView = kasaController.processProduct(product);

        assertEquals("register", modelAndView.getViewName());
        assertEquals("Вже є товар з такою назвою.", modelAndView.getModel().get("error"));
        verify(productRepository, never()).save(any());
    }

    @Test
    void testShowProductPage() {
        int productId = 1;
        Model model = mock(Model.class);
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        String viewName = kasaController.showProductPage(productId, model);

        assertEquals("product", viewName);
        verify(model).addAttribute("product", product);
    }

    @Test
    void testShowProductPageWithInvalidProductId() {
        int productId = 1;
        Model model = mock(Model.class);
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        String viewName = kasaController.showProductPage(productId, model);

        assertEquals("redirect:/", viewName);
        verify(model, never()).addAttribute(any(), any());
    }
}