package com.bdsk.kasa.repository;

import com.bdsk.kasa.domain.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

@Repository
public class ProductRepository implements GenericRepository<Product, Integer> {

    private final String filePath = "products.json";
    private final Gson gson = new Gson();

    @Override
    public Product save(Product productToSave) {
        List<Product> allProducts = findAll();
        allProducts.removeIf(product -> product.getId() == productToSave.getId());
        allProducts.add(productToSave);

        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(allProducts, writer);
        } catch (IOException e) {
            // TODO: Add exception handling
        }
        return productToSave;
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return findAll().stream()
                .filter(product -> product.getId() == id)
                .findFirst();
    }

    public Optional<Product> findByName(String name) {
        return findAll().stream()
                .filter(product -> product.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<Product> findAll() {
        Type productListType = new TypeToken<ArrayList<Product>>(){}.getType();
        try (Reader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, productListType);
        } catch (IOException e) {
            // TODO: Add exception handling
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteById(Integer id) {
        List<Product> allProducts = findAll();
        allProducts.removeIf(product -> product.getId() == id);

        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(allProducts, writer);
        } catch (IOException e) {
            // TODO: Add exception handling
        }
    }
}