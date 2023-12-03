package com.bdsk.kasa.domain;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void testEquals() {
        User user1 = createUser(1, "John Doe", "john", "password123", true, true, true, true);
        User user2 = createUser(1, "John Doe", "john", "password123", true, true, true, true);
        User user3 = createUser(2, "Jane Doe", "jane", "pass456", true, true, true, true);

        assertEquals(user1, user2);

        assertNotEquals(user1, user3);
    }

    @Test
    void testHashCode() {
        User user1 = createUser(1, "John Doe", "john", "password123", true, true, true, true);
        User user2 = createUser(1, "John Doe", "john", "password123", true, true, true, true);

        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testUserDetailsImplementation() {
        User user = createUser(1, "John Doe", "john", "password123", true, true, true, true);

        assertEquals(user.getUsername(), "john");
        assertEquals(user.getPassword(), "password123");
        assertTrue(user.isEnabled());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isAccountNonLocked());

        assertEquals(1, user.getAuthorities().size());
        assertTrue(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    private User createUser(
            int id, String displayName, String username, String password,
            boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked) {
        User user = new User();
        user.setId(id);
        user.setDisplayName(displayName);
        user.setUsername(username);
        user.setPassword(password);
        user.setEnabled(enabled);
        user.setAccountNonExpired(accountNonExpired);
        user.setCredentialsNonExpired(credentialsNonExpired);
        user.setAccountNonLocked(accountNonLocked);
        return user;
    }
}
