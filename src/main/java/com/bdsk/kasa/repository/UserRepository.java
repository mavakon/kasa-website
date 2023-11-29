package com.bdsk.kasa.repository;

import com.bdsk.kasa.domain.User;
import com.google.gson.Gson;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
public class UserRepository implements GenericRepository<User, Integer> {

    private final Map<Integer, Long> indexMap = new HashMap<>();
    private final String filePath = "users.json";
    private final Gson gson = new Gson();

    public UserRepository() {
        buildIndexMap();
    }

    private void buildIndexMap() {
        indexMap.clear();
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            long position = 0;
            String line;
            while ((line = file.readLine()) != null) {
                User user = gson.fromJson(line, User.class);
                indexMap.put(user.getId(), position);
                position = file.getFilePointer();
            }
        } catch (IOException e) {
            // TODO Add exception handling
        }
    }

    public User save(User userToSave) {
        List<User> allUsers = new ArrayList<>();
        boolean isUserToSavePresent = false;
        int idToSave = userToSave.getId();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = gson.fromJson(line, User.class);
                if (!(user.getId() == idToSave)) {
                    allUsers.add(user);
                } else {
                    allUsers.add(userToSave);
                    isUserToSavePresent = true;
                }
            }
        } catch (IOException e) {
            // TODO Add exception handling
        }
        if (!isUserToSavePresent) {
            allUsers.add(userToSave);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (User user : allUsers) {
                writer.write(gson.toJson(user));
                writer.newLine();
            }
        } catch (IOException e) {
            // TODO Add exception handling
        }
        buildIndexMap();
        return userToSave;
    }

    @Override
    public Optional<User> findById(Integer id) {
        if (!indexMap.containsKey(id)) {
            return Optional.empty();
        }
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            file.seek(indexMap.get(id));
            User user = gson.fromJson(file.readLine(), User.class);
            return Optional.of(user);
        } catch (IOException e) {
            // TODO Add exception handling
            return Optional.empty();
        }

    }

    public Optional<User> findByLogin(String login) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = gson.fromJson(line, User.class);
                if (user.getLogin().equals(login)) {
                    return Optional.of(user);
                }
            }
        } catch (IOException e) {
            // TODO Add exception handling
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = gson.fromJson(line, User.class);
                users.add(user);
            }
        } catch (IOException e) {
            // TODO Add exception handling
        }
        return users;
    }

    @Override
    public void deleteById(Integer id) {

        if (!indexMap.containsKey(id)) {
            return;
        }

        List<User> allUsers = new ArrayList<>();
        boolean isUserFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = gson.fromJson(line, User.class);
                if (!(user.getId() == id)) {
                    allUsers.add(user);
                } else {
                    isUserFound = true;
                }
            }
        } catch (IOException e) {
            // TODO Add exception handling
        }

        if (!isUserFound) {
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (User user : allUsers) {
                writer.write(gson.toJson(user));
                writer.newLine();
            }
        } catch (IOException e) {
            // TODO Add exception handling
        }

        buildIndexMap();
    }
}