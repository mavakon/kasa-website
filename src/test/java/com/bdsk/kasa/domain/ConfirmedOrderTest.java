package com.bdsk.kasa.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConfirmedOrderTest {

    @Test
    void testGetAndSetId() {
        ConfirmedOrder confirmedOrder = new ConfirmedOrder();

        // Set and verify the ID
        confirmedOrder.setId(123);
        assertEquals(123, confirmedOrder.getId());
    }

    @Test
    void testGetAndSetAuthorId() {
        ConfirmedOrder confirmedOrder = new ConfirmedOrder();

        // Set and verify the author ID
        confirmedOrder.setAuthorId(456);
        assertEquals(456, confirmedOrder.getAuthorId());
    }

    @Test
    void testInheritedGetAndSetProducts() {
        ConfirmedOrder confirmedOrder = new ConfirmedOrder();

        Product product1 = createProduct(1, "Product A", "Description A", 10.0, 1001);
        Product product2 = createProduct(2, "Product B", "Description B", 20.0, 1002);
        confirmedOrder.getProducts().put(product1, 2);
        confirmedOrder.getProducts().put(product2, 3);

        assertEquals(2, confirmedOrder.getProducts().get(product1));
        assertEquals(3, confirmedOrder.getProducts().get(product2));
    }

    @Test
    void testInheritedGetAndSetTotalPrice() {
        ConfirmedOrder confirmedOrder = new ConfirmedOrder();

        confirmedOrder.setPrice(50.0);

        assertEquals(50.0, confirmedOrder.getPrice());
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
