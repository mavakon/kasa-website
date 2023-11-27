package com.bdsk.kasa.services;

import com.bdsk.kasa.domain.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private List<Product> products = new ArrayList<>();

    public List<Product> Productlist() {return products;}

    public void saveProduct(Product product){
        products.add(product);
    }

    public void deleteProduct(int id){
        products.removeIf(product -> (product.getId() == id));
    }
}
