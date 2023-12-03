package com.bdsk.kasa.web;

import com.bdsk.kasa.domain.User;
import com.bdsk.kasa.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Model model;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adminController = new AdminController(userRepository);
    }

    @Test
    void testAdminPanel() {
        String result = adminController.adminPanel();
        assertEquals("admin", result);
    }

    @Test
    void testUserDeleteSuccess() {
        int userId = 1;
        when(userRepository.deleteById(userId)).thenReturn(true);

        String result = adminController.userDelete(userId, model);

        verify(model).addAttribute("success", "Видалено користувача з ID: " + userId);
        verify(userRepository).deleteById(userId);
        assertEquals("redirect:/admin", result);
    }

    @Test
    void testUserToSellerSuccess() {
        int userId = 1;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);

        String result = adminController.userToSeller(userId, model);

        verify(model).addAttribute("success", "Змінені права користувача з ID: " + userId);
        verify(userRepository).save(user);
        assertEquals("redirect:/admin", result);
    }

    @Test
    void testUserToSellerFailure() {
        int userId = 1;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        String result = adminController.userToSeller(userId, model);

        verify(model).addAttribute("error", "Не знайдено користувача з ID" + userId);
        verify(userRepository, never()).save(any());
        assertEquals("redirect:/admin", result);
    }

    @Test
    void testSellerToUser() {
        int userId = 1;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);

        String result = adminController.sellerToUser(userId, model);

        verify(model).addAttribute("success", "Змінені права користувача з ID: " + userId);
        verify(userRepository).save(user);
        assertEquals("redirect:/admin", result);
    }
}
