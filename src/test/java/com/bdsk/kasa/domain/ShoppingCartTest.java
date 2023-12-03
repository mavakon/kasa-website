package com.bdsk.kasa.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCartTest {

    @Test
    void testConstructorAndRecalculateTotalPrice() {
        Map<Product, Integer> products = new HashMap<>();
        Product product1 = createProduct(1, "Product A", "Description A", 10.0, 1001);
        Product product2 = createProduct(2, "Product B", "Description B", 20.0, 1002);
        products.put(product1, 2);
        products.put(product2, 3);

        ShoppingCart shoppingCart = new ShoppingCart(products);

        assertEquals(products, shoppingCart.getProducts());
        assertEquals(2 * 10.0 + 3 * 20.0, shoppingCart.getPrice());
    }

    @Test
    void testAddProduct() {
        ShoppingCart shoppingCart = new ShoppingCart(new HashMap<>());
        Product product1 = createProduct(1, "Product A", "Description A", 10.0, 1001);

        shoppingCart.addProduct(product1);

        assertTrue(shoppingCart.getProducts().containsKey(product1));
        assertEquals(1, shoppingCart.getProducts().get(product1));
        assertEquals(10.0, shoppingCart.getPrice());
    }

    @Test
    void testRemoveProduct() {
        Map<Product, Integer> products = new HashMap<>();
        Product product1 = createProduct(1, "Product A", "Description A", 10.0, 1001);
        products.put(product1, 2);

        ShoppingCart shoppingCart = new ShoppingCart(products);

        shoppingCart.removeProduct(product1);

        assertTrue(shoppingCart.getProducts().containsKey(product1));
        assertEquals(1, shoppingCart.getProducts().get(product1));
        assertEquals(10.0, shoppingCart.getPrice());
    }

    @Test
    void testRemoveProduct_NotInCart() {
        ShoppingCart shoppingCart = new ShoppingCart(new HashMap<>());
        Product product1 = createProduct(1, "Product A", "Description A", 10.0, 1001);

        shoppingCart.removeProduct(product1);

        assertFalse(shoppingCart.getProducts().containsKey(product1));
        assertEquals(0.0, shoppingCart.getPrice());
    }

    @Test
    void testRemoveProduct_DecreaseQuantity() {
        Map<Product, Integer> products = new HashMap<>();
        Product product1 = createProduct(1, "Product A", "Description A", 10.0, 1001);
        products.put(product1, 2);

        ShoppingCart shoppingCart = new ShoppingCart(products);

        shoppingCart.removeProduct(product1);

        assertTrue(shoppingCart.getProducts().containsKey(product1));
        assertEquals(1, shoppingCart.getProducts().get(product1));
        assertEquals(10.0, shoppingCart.getPrice());
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