package com.bdsk.kasa.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductTest {

    @Test
    void testEquals() {
        Product product1 = createProduct(1, "Product A", "Description A", 10.0, 1001);
        Product product2 = createProduct(1, "Product A", "Description A", 10.0, 1001);
        Product product3 = createProduct(2, "Product B", "Description B", 20.0, 1002);

        assertEquals(product1, product2);

        assertNotEquals(product1, product3);
    }

    @Test
    void testHashCode() {
        Product product1 = createProduct(1, "Product A", "Description A", 10.0, 1001);
        Product product2 = createProduct(1, "Product A", "Description A", 10.0, 1001);

        assertEquals(product1.hashCode(), product2.hashCode());
    }

    private Product createProduct(int id, String name, String description, double price, int authorId) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setAuthorId(authorId);
        return product;
    }

    @Test
    void testProductSetters() {
        Product product = mock(Product.class);

        when(product.getId()).thenReturn(1);
        when(product.getName()).thenReturn("Test Product");
        when(product.getDescription()).thenReturn("Test Description");
        when(product.getPrice()).thenReturn(15.0);
        when(product.getAuthorId()).thenReturn(1003);

        assertEquals(1, product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals("Test Description", product.getDescription());
        assertEquals(15.0, product.getPrice());
        assertEquals(1003, product.getAuthorId());
    }
}