package com.bdsk.kasa.domain;

import com.bdsk.kasa.domain.interfaces.Priceable;

import java.util.Collection;
import java.util.List;

public class Order implements Priceable {
    private List<Product> products;
    private double totalPrice;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public double getPrice() {
        return totalPrice;
    }

    @Override
    public void setPrice(double price) {
        this.totalPrice = price;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void addProducts(Collection<Product> products) {
        this.products.addAll(products);
        recalculateTotalPrice();
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
        recalculateTotalPrice();
    }

    public void recalculateTotalPrice() {
        this.totalPrice = products.stream().mapToDouble(Product::getPrice).sum();
    }

}