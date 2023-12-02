package com.bdsk.kasa.service;

import com.bdsk.kasa.domain.Product;
import com.bdsk.kasa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findByName(String name) {
        List<Product> allProducts = productRepository.findAll();

        return allProducts.stream()
                .filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()))
                .sorted(Comparator.comparingInt((Product product) -> similarityScore(product.getName(), name)).reversed())
                .collect(Collectors.toList());
    }

    private int similarityScore(String productName, String searchTerm) {
        productName = productName.toLowerCase();
        searchTerm = searchTerm.toLowerCase();

        int score = 0;
        for (char c : searchTerm.toCharArray()) {
            if (productName.indexOf(c) >= 0) {
                score++;
            }
        }
        return score;
    }
}
