package br.com.iuryalmeida.TaskEase.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private IUserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() throws Exception {
        UserModel userModel = new UserModel();
        userModel.setUsername("admin");
        userModel.setPassword("admin");

        when(userRepository.findByUsername("admin")).thenReturn(null);
        when(userRepository.save(any(UserModel.class))).thenReturn(userModel);

        mockMvc.perform(post("/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userModel)))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateUser_UserAlreadyExists() throws Exception {
        UserModel userModel = new UserModel();
        userModel.setUsername("admin1");
        userModel.setPassword("password");

        when(userRepository.findByUsername("admin1")).thenReturn(userModel);

        mockMvc.perform(post("/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userModel)))
                .andExpect(status().isInternalServerError());
    }
}