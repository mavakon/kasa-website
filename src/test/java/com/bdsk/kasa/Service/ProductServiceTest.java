package com.bdsk.kasa.Service;

import com.bdsk.kasa.domain.Product;
import com.bdsk.kasa.repository.ProductRepository;
import com.bdsk.kasa.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void searchByName_WithMatchingName_ShouldReturnSortedProducts() {
        Product prod1 = new Product();
        prod1.setId(1);
        prod1.setName("Apple iPhone");
        prod1.setDescription("Smartphone");
        prod1.setPrice(1000.0);

        Product prod2 = new Product();
        prod2.setId(2);
        prod2.setName("MacBook Pro");
        prod2.setDescription("Laptop");
        prod2.setPrice(2000.0);

        Product prod3 = new Product();
        prod3.setId(3);
        prod3.setName("Apple Watch");
        prod3.setDescription("Smartwatch");
        prod3.setPrice(300.0);

        String searchName = "apple";

        List<Product> mockProducts = Arrays.asList(prod1, prod2, prod3);
        when(productRepository.findAll()).thenReturn(mockProducts);

        List<Product> result = productService.searchByName(searchName);

        assertEquals(2, result.size());
    }

    @Test
    void searchByName_WithNoMatchingName_ShouldReturnEmptyList() {
        Product prod1 = new Product();
        prod1.setId(1);
        prod1.setName("Apple iPhone");
        prod1.setDescription("Smartphone");
        prod1.setPrice(1000.0);

        Product prod2 = new Product();
        prod2.setId(2);
        prod2.setName("MacBook Pro");
        prod2.setDescription("Laptop");
        prod2.setPrice(2000.0);

        Product prod3 = new Product();
        prod3.setId(3);
        prod3.setName("Apple Watch");
        prod3.setDescription("Smartwatch");
        prod3.setPrice(300.0);

        String searchName = "xyz";

        List<Product> mockProducts = Arrays.asList(prod1, prod2, prod3);
        when(productRepository.findAll()).thenReturn(mockProducts);

        List<Product> result = productService.searchByName(searchName);

        assertEquals(0, result.size());
    }
}