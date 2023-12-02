package com.bdsk.kasa.domain;

import java.util.Map;

public class ShoppingCart extends Order {

    public ShoppingCart(Map<Product, Integer> products) {
        this.products = products;
        this.recalculateTotalPrice();
    }

    public void recalculateTotalPrice() {
        this.totalPrice = products.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public void addProduct(Product product) {
        if (product == null) {
            return;
        }

        if (products.containsKey(product)) {
            products.put(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }
        this.recalculateTotalPrice();
    }

    public void removeProduct(Product product) {
        if (product == null) {
            return;
        }
        if (!products.containsKey(product)) {
            return;
        }
        if (products.get(product) > 1) {
            products.put(product, products.get(product) - 1);
        } else {
            products.remove(product);
        }
        this.recalculateTotalPrice();
    }

}
