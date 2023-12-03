package com.bdsk.kasa.repository;

import com.bdsk.kasa.domain.Product;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

public class ProductMapSerializer implements JsonSerializer<Map<Product, Integer>> {

    @Override
    public JsonElement serialize(Map<Product, Integer> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray productArray = new JsonArray();

        for (Map.Entry<Product, Integer> entry : src.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();

            JsonObject productObject = new JsonObject();
            productObject.add("product", context.serialize(product));
            productObject.addProperty("quantity", quantity);

            productArray.add(productObject);
        }

        return productArray;
    }
}