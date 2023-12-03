package com.bdsk.kasa.domain;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class OrderTest {

    @Test
    void testGetAndSetProducts() {
        Order order = new Order();

        Map<Product, Integer> productMap = new HashMap<>();
        Product product1 = createProduct(1, "Product A", "Description A", 10.0, 1001);
        Product product2 = createProduct(2, "Product B", "Description B", 20.0, 1002);
        productMap.put(product1, 2);
        productMap.put(product2, 3);

        order.setProducts(productMap);

        assertEquals(productMap, order.getProducts());
    }

    @Test
    void testGetAndSetTotalPrice() {
        Order order = new Order();

        order.setPrice(50.0);

        assertEquals(50.0, order.getPrice());
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
}
