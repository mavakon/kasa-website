package com.bdsk.kasa.Service;

import com.bdsk.kasa.domain.User;
import com.bdsk.kasa.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCurrentUserId_AuthenticatedUser_ShouldReturnUserId() {
        User currentUser = new User();
        currentUser.setId(1);
        currentUser.setUsername("testUser");
        currentUser.setPassword("password");
        setAuthentication(currentUser);

        int resultUserId = userService.getCurrentUser().getId();

        assertEquals(1, resultUserId);
    }

    @Test
    void getCurrentUserId_UnauthenticatedUser_ShouldThrowException() {
        setAuthentication(null);

        assertThrows(IllegalStateException.class, () -> userService.getCurrentUser().getId());
    }

    @Test
    void getCurrentUser_AuthenticatedUser_ShouldReturnCurrentUser() {
        User currentUser = new User();
        currentUser.setId(1);
        currentUser.setUsername("testUser");
        currentUser.setPassword("password");
        setAuthentication(currentUser);

        setAuthentication(currentUser);

        User resultUser = userService.getCurrentUser();

        assertNotNull(resultUser);
        assertEquals(currentUser.getId(), resultUser.getId());
    }

    @Test
    void getCurrentUser_UnauthenticatedUser_ShouldThrowException() {
        setAuthentication(null);

        assertThrows(IllegalStateException.class, () -> userService.getCurrentUser());
    }

    private void setAuthentication(User user) {
        if (user != null) {
            when(authentication.isAuthenticated()).thenReturn(true);
            when(authentication.getPrincipal()).thenReturn(user);
        } else {
            when(authentication.isAuthenticated()).thenReturn(false);
        }

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
    }
}
