package com.bdsk.kasa.repository;

import com.bdsk.kasa.domain.Product;
import com.google.gson.Gson;
import org.springframework.stereotype.Repository;


import java.io.*;
import java.util.*;

@Repository
public class ProductRepository implements GenericRepository<Product, Integer> {

    private final Map<Integer, Long> indexMap = new HashMap<>();
    private final String filePath = "products.json";
    private final Gson gson = new Gson();

    public ProductRepository() {
        buildIndexMap();
    }

    private void buildIndexMap() {
        indexMap.clear();
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            long position = 0;
            String line;
            while ((line = file.readLine()) != null) {
                Product product = gson.fromJson(line, Product.class);
                indexMap.put(product.getId(), position);
                position = file.getFilePointer();
            }
        } catch (IOException e) {
            // TODO Add exception handling
        }
    }

    public Product save(Product productToSave) {
        List<Product> allProducts = new ArrayList<>();
        boolean isProductToSavePresent = false;
        int idToSave = productToSave.getId();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Product product = gson.fromJson(line, Product.class);
                if (!(product.getId() == idToSave)) {
                    allProducts.add(product);
                } else {
                    allProducts.add(productToSave);
                    isProductToSavePresent = true;
                }
            }
        } catch (IOException e) {
            // TODO Add exception handling
        }
        if (!isProductToSavePresent) {
            allProducts.add(productToSave);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Product product : allProducts) {
                writer.write(gson.toJson(product));
                writer.newLine();
            }
        } catch (IOException e) {
            // TODO Add exception handling
        }
        buildIndexMap();
        return productToSave;
    }

    @Override
    public Optional<Product> findById(Integer id) {
        if (!indexMap.containsKey(id)) {
            return Optional.empty();
        }
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            file.seek(indexMap.get(id));
            Product product = gson.fromJson(file.readLine(), Product.class);
            return Optional.of(product);
        } catch (IOException e) {
            // TODO Add exception handling
            return Optional.empty();
        }

    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Product product = gson.fromJson(line, Product.class);
                products.add(product);
            }
        } catch (IOException e) {
            // TODO Add exception handling
        }
        return products;
    }

    @Override
    public void deleteById(Integer id) {

        if (!indexMap.containsKey(id)) {
            return;
        }

        List<Product> allProducts = new ArrayList<>();
        boolean isProductFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Product product = gson.fromJson(line, Product.class);
                if (!(product.getId() == id)) {
                    allProducts.add(product);
                } else {
                    isProductFound = true;
                }
            }
        } catch (IOException e) {
            // TODO Add exception handling
        }

        if (!isProductFound) {
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Product product : allProducts) {
                writer.write(gson.toJson(product));
                writer.newLine();
            }
        } catch (IOException e) {
            // TODO Add exception handling
        }

        buildIndexMap();
    }
}
