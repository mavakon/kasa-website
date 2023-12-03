package com.bdsk.kasa.repository;

import com.bdsk.kasa.domain.Product;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ProductMapDeserializer implements JsonDeserializer<Map<Product, Integer>> {

    @Override
    public Map<Product, Integer> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Map<Product, Integer> productMap = new HashMap<>();
        JsonArray productArray = json.getAsJsonArray();

        for (JsonElement element : productArray) {
            JsonObject productObject = element.getAsJsonObject();
            Product product = context.deserialize(productObject.get("product"), Product.class);
            Integer quantity = productObject.get("quantity").getAsInt();
            productMap.put(product, quantity);
        }

        return productMap;
    }
}