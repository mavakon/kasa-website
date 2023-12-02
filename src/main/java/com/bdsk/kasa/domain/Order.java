package com.bdsk.kasa.domain;

import com.bdsk.kasa.domain.interfaces.Priceable;

import java.util.*;

public class Order implements Priceable {
    protected Map<Product, Integer> products = new HashMap<>();
    protected double totalPrice;

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
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

}