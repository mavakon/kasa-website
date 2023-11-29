package com.bdsk.kasa.repository;

import com.bdsk.kasa.domain.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

@Repository
public class UserRepository implements GenericRepository<User, Integer> {

    private final String filePath = "users.json";
    private final Gson gson = new Gson();

    @Override
    public User save(User userToSave) {
        List<User> allUsers = findAll();
        allUsers.removeIf(user -> user.getId() == userToSave.getId());
        allUsers.add(userToSave);

        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(allUsers, writer);
        } catch (IOException e) {
            // TODO: Add exception handling
        }
        return userToSave;
    }
    @Override
    public Optional<User> findById(Integer id) {
        return findAll().stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

    public Optional<User> findByLogin(String login) {
        return findAll().stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        try (Reader reader = new FileReader(filePath)) {
            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            return gson.fromJson(reader, userListType);
        } catch (IOException e) {
            // TODO: Add exception handling
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteById(Integer id) {
        List<User> allUsers = findAll();
        allUsers.removeIf(user -> user.getId() == id);

        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(allUsers, writer);
        } catch (IOException e) {
            // TODO: Add exception handling
        }
    }
}