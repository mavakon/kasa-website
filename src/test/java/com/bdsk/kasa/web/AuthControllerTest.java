package com.bdsk.kasa.web;

import com.bdsk.kasa.domain.User;
import com.bdsk.kasa.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Model model;

    @InjectMocks
    private AuthController authController;

    @Test
    public void testShowRegistrationForm() {
        String viewName = authController.showRegistrationForm(model);

        assertEquals("registration", viewName);
        verify(model).addAttribute(eq("user"), any(User.class));
    }

    @Test
    public void testAcceptRegistrationFormUserExists() {
        User existingUser = new User();
        existingUser.setUsername("existingUser");

        when(userRepository.findByUsername(existingUser.getUsername())).thenReturn(Optional.of(existingUser));

        ModelAndView modelAndView = authController.acceptRegistrationForm(existingUser);

        assertEquals("registration", modelAndView.getViewName());
        assertEquals("The user with that username already exists!", modelAndView.getModel().get("error"));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testAcceptRegistrationFormUserDoesNotExist() {
        User newUser = new User();
        newUser.setUsername("newUser");

        when(userRepository.findByUsername(newUser.getUsername())).thenReturn(Optional.empty());

        ModelAndView modelAndView = authController.acceptRegistrationForm(newUser);

        assertEquals("home", modelAndView.getViewName());
        assertEquals(newUser, modelAndView.getModel().get("user"));
        verify(userRepository).save(eq(newUser));
    }
}