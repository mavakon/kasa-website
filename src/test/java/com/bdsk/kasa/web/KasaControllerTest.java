package com.bdsk.kasa.web;

import com.bdsk.kasa.domain.Product;
import com.bdsk.kasa.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@ExtendWith(MockitoExtension.class)
public class KasaControllerTest {

    private MockMvc mockMvc;
    @Mock
    private ProductRepository productRepository;

    @Mock
    private Model model;

    @InjectMocks
    private KasaController KasaController;

    @Test
    public void testProducts() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        String viewName = KasaController.products(model);

        assertEquals("home", viewName);
        verify(model).addAttribute(eq("productList"), any());
    }

    @Test
    public void testShowProductForm() {
        String viewName = KasaController.showProductForm(model);

        assertEquals("prod", viewName);
        verify(model).addAttribute(eq("product"), any());
    }

    @Test
    public void testProfile() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(KasaController).build();

        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"));
    }
    @Test
    public void testShowProductPage() {
        int productId = 1;
        Product mockProduct = new Product();
        mockProduct.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        String viewName = KasaController.showProductPage(productId, model);

        assertEquals("product", viewName);
        verify(model).addAttribute(eq("product"), eq(mockProduct));
    }

    @Test
    public void testShowProductPageNotFound() {
        int productId = 1;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        String viewName = KasaController.showProductPage(productId, model);

        assertEquals("home", viewName);
        verify(model, never()).addAttribute(eq("product"), any());
    }

    @Test
    public void testProcessProduct() {
        Product product = new Product();

        String viewName = KasaController.processProduct(product);

        assertEquals("redirect:/kasa/", viewName);
        verify(productRepository).save(product);
    }
}