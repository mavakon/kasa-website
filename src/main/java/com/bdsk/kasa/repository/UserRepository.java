package com.bdsk.kasa.repository;

import com.bdsk.kasa.domain.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements GenericRepository<User, Integer> {

    private static final String filePath = "users.json";
    private static final Gson gson = new Gson();
    private static final Object fileLock = new Object();

    @Override
    public User save(User userToSave) {
        synchronized (fileLock) {
            List<User> allUsers = findAll();
            allUsers.removeIf(user -> user.getId() == userToSave.getId());
            allUsers.add(userToSave);

            try (Writer writer = new FileWriter(filePath)) {
                gson.toJson(allUsers, writer);
            } catch (IOException e) {
                // TODO: Add exception handling
            }
        }
        return userToSave;
    }
    @Override
    public Optional<User> findById(Integer id) {
        return findAll().stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

    public Optional<User> findByUsername(String username) {
        return findAll().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        synchronized (fileLock) {
            try (Reader reader = new FileReader(filePath)) {
                Type userListType = new TypeToken<ArrayList<User>>() {
                }.getType();
                return gson.fromJson(reader, userListType);
            } catch (IOException e) {
                // TODO: Add exception handling
            }
        }
        return new ArrayList<>();
    }

    @Override
    public boolean deleteById(Integer id) {
        boolean deleted;
        synchronized (fileLock) {
            List<User> allUsers = findAll();
            deleted = allUsers.removeIf(user -> user.getId() == id);

            try (Writer writer = new FileWriter(filePath)) {
                gson.toJson(allUsers, writer);
            } catch (IOException e) {
                // TODO: Add exception handling
            }
        }
        return deleted;
    }

}