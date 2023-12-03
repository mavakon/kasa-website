package com.bdsk.kasa.repository;

import com.bdsk.kasa.domain.ConfirmedOrder;
import com.bdsk.kasa.domain.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ConfirmedOrderRepository implements GenericRepository<ConfirmedOrder, Integer> {

    private static final String filePath = "orders.json";
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(new TypeToken<Map<Product, Integer>>(){}.getType(), new ProductMapSerializer())
            .registerTypeAdapter(new TypeToken<Map<Product, Integer>>(){}.getType(), new ProductMapDeserializer())
            .create();
    private static final Object fileLock = new Object();

    @Override
    public ConfirmedOrder save(ConfirmedOrder confirmedOrderToSave) {
        synchronized (fileLock) {
            List<ConfirmedOrder> allConfirmedOrders = findAll();
            allConfirmedOrders.removeIf(order -> order.getId() == confirmedOrderToSave.getId());
            allConfirmedOrders.add(confirmedOrderToSave);

            try (Writer writer = new FileWriter(filePath)) {
                gson.toJson(allConfirmedOrders, writer);
            } catch (IOException e) {
                // TODO: Add exception handling
            }
        }
        return confirmedOrderToSave;
    }

    @Override
    public Optional<ConfirmedOrder> findById(Integer id) {
        return findAll().stream()
                .filter(confirmedOrder -> confirmedOrder.getId() == id)
                .findFirst();
    }

    @Override
    public List<ConfirmedOrder> findAll() {
        synchronized (fileLock) {
            Type confirmedOrderListType = new TypeToken<ArrayList<ConfirmedOrder>>() {
            }.getType();
            try (Reader reader = new FileReader(filePath)) {
                return gson.fromJson(reader, confirmedOrderListType);
            } catch (IOException e) {
                // TODO: Add exception handling
            }
        }
        return new ArrayList<>();
    }

    @Override
    public boolean deleteById(Integer id) {
        synchronized (fileLock) {
            List<ConfirmedOrder> allConfirmedOrders = findAll();
            allConfirmedOrders.removeIf(confirmedOrder -> confirmedOrder.getId() == id);

            try (Writer writer = new FileWriter(filePath)) {
                gson.toJson(allConfirmedOrders, writer);
            } catch (IOException e) {
                // TODO: Add exception handling
            }
        }
        return false;
    }
}