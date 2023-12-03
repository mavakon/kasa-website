package com.bdsk.kasa.repository;

import com.bdsk.kasa.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;



@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @InjectMocks
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() throws IOException {
        Files.deleteIfExists(Paths.get("users.json"));
    }

    @Test
    public void saveTest() {
        User userToSave = new User();
        userToSave.setId(1);
        userToSave.setUsername("user");
        userToSave.setPassword("123");

        userRepository.save(userToSave);

        Optional<User> savedUser = userRepository.findById(1);
        assertTrue(savedUser.isPresent());
        assertEquals(userToSave, savedUser.get());
    }

    @Test
    public void findByIdTest() {
        User userToSave = new User();
        userToSave.setId(1);
        userToSave.setUsername("user");
        userToSave.setPassword("123");

        userRepository.save(userToSave);

        Optional<User> savedUser = userRepository.findById(1);
        assertTrue(savedUser.isPresent());
        assertEquals(userToSave, savedUser.get());
    }

    @Test
    public void findByUsernameTest() {
        User userToSave = new User();
        userToSave.setId(1);
        userToSave.setUsername("user");
        userToSave.setPassword("123");

        userRepository.save(userToSave);

        Optional<User> savedUser = userRepository.findByUsername("user");
        assertTrue(savedUser.isPresent());
        assertEquals(userToSave, savedUser.get());
    }


    @Test
    public void findAllTest() {

        User user1 = new User();
        user1.setId(1);
        user1.setUsername("user1");
        user1.setPassword("123");

        User user2 = new User();
        user2.setId(2);
        user2.setUsername("user2");
        user2.setPassword("1234");

        List<User> usersToSave = Arrays.asList(user1, user2);
        usersToSave.forEach(userRepository::save);

        List<User> allUsers = userRepository.findAll();
        assertEquals(usersToSave.size(), allUsers.size());
        assertTrue(allUsers.containsAll(usersToSave));
    }
    @Test
    public void deleteByIdTest() {
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("user1");
        user1.setPassword("123");

        userRepository.save(user1);

        userRepository.deleteById(1);

        List<User> expectedUsers = new ArrayList<>();
        assertEquals(expectedUsers, userRepository.findAll());
    }
}