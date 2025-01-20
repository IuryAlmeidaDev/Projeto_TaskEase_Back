package br.com.iuryalmeida.TaskEase.filter;

import java.util.Base64;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.iuryalmeida.TaskEase.user.IUserRepository;
import br.com.iuryalmeida.TaskEase.user.UserModel;

@WebMvcTest(FilterTaskAuth.class)
public class FilterTaskAuthTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private IUserRepository userRepository;

    @InjectMocks
    private FilterTaskAuth filterTaskAuth;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new Object())
                .addFilters(filterTaskAuth)
                .build();
    }

    @Test
    public void testFilterTaskAuth_Success() throws Exception {
        String username = "testuser";
        String password = "password";
        String encodedCredentials = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

        UserModel userModel = new UserModel();
        userModel.setUsername(username);
        userModel.setPassword(BCrypt.withDefaults().hashToString(12, password.toCharArray()));
        userModel.setId(UUID.randomUUID());

        when(userRepository.findByUsername(username)).thenReturn(userModel);

        mockMvc.perform(get("/tasks/")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedCredentials)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFilterTaskAuth_Unauthorized() throws Exception {
        String username = "testuser";
        String password = "wrongpassword";
        String encodedCredentials = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

        UserModel userModel = new UserModel();
        userModel.setUsername(username);
        userModel.setPassword(BCrypt.withDefaults().hashToString(12, "password".toCharArray()));
        userModel.setId(UUID.randomUUID());

        when(userRepository.findByUsername(username)).thenReturn(userModel);

        mockMvc.perform(get("/tasks/")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedCredentials)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testFilterTaskAuth_UserNotFound() throws Exception {
        String username = "nonexistentuser";
        String password = "password";
        String encodedCredentials = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

        when(userRepository.findByUsername(username)).thenReturn(null);

        mockMvc.perform(get("/tasks/")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedCredentials)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}