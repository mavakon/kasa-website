package com.bdsk.kasa.domain;

import java.util.UUID;

public class Product {
    private int id = UUID.randomUUID().hashCode();
    private String name;
    private String description;
    private int price;
    private String author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (getId() != product.getId()) return false;
        if (getPrice() != product.getPrice()) return false;
        if (!getName().equals(product.getName())) return false;
        if (!getDescription().equals(product.getDescription())) return false;
        return getAuthor().equals(product.getAuthor());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getPrice();
        result = 31 * result + getAuthor().hashCode();
        return result;
    }
}
