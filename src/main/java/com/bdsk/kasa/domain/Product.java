package com.bdsk.kasa.domain;

import com.bdsk.kasa.domain.interfaces.*;
import java.util.UUID;

public class Product implements Identifiable, Priceable, Describable {
    private int id = UUID.randomUUID().hashCode();
    private String name;
    private String description;
    private double price;
    private int authorId;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public double getPrice() {
        return price;
    }
    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (getId() != product.getId()) return false;
        if (Double.compare(getPrice(), product.getPrice()) != 0) return false;
        if (getAuthorId() != product.getAuthorId()) return false;
        if (!getName().equals(product.getName())) return false;
        return getDescription().equals(product.getDescription());
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getId();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        temp = Double.doubleToLongBits(getPrice());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getAuthorId();
        return result;
    }
}
