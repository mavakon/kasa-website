package com.bdsk.kasa.config;

import com.bdsk.kasa.service.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
@Import(SecurityConfig.class)
public class SecurityConfigTest {

    @Mock
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "testuser", password = "testpassword", roles = "USER")
    public void testAuthenticatedUserCanAccessSecuredResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/secured-resource"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUnauthenticatedUserCannotAccessSecuredResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/secured-resource"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void testLoginFormIsDisplayed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"));
    }
}