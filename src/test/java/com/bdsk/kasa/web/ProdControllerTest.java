package com.bdsk.kasa.web;

import com.bdsk.kasa.domain.Product;
import com.bdsk.kasa.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdControllerTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private Model model;

    @InjectMocks
    private ProdController prodController;

    @Test
    public void testProducts() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        String viewName = prodController.products(model);

        assertEquals("home", viewName);
        verify(model).addAttribute(eq("productList"), any());
    }

    @Test
    public void testShowProductForm() {
        String viewName = prodController.showProductForm(model);

        assertEquals("prod", viewName);
        verify(model).addAttribute(eq("product"), any());
    }

    @Test
    public void testShowProductPage() {
        int productId = 1;
        Product mockProduct = new Product();
        mockProduct.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        String viewName = prodController.showProductPage(productId, model);

        assertEquals("product", viewName);
        verify(model).addAttribute(eq("product"), eq(mockProduct));
    }

    @Test
    public void testShowProductPageNotFound() {
        int productId = 1;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        String viewName = prodController.showProductPage(productId, model);

        assertEquals("home", viewName);
        verify(model, never()).addAttribute(eq("product"), any());
    }

    @Test
    public void testProcessProduct() {
        Product product = new Product();

        String viewName = prodController.processProduct(product);

        assertEquals("redirect:/", viewName);
        verify(productRepository).save(product);
    }
}