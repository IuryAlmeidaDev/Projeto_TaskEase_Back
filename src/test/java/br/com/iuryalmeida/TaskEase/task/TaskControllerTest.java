package br.com.iuryalmeida.TaskEase.task;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private ITaskRepository taskRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTask() throws Exception {
        TaskModel taskModel = new TaskModel();
        taskModel.setEndAt(LocalDateTime.now().plusDays(1));
        taskModel.setStatus(TaskStatus.pendente);

        when(taskRepository.save(any(TaskModel.class))).thenReturn(taskModel);

        mockMvc.perform(post("/tasks/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(taskModel)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetTasks() throws Exception {
        UUID idUser = UUID.randomUUID();
        when(taskRepository.findByIdUser(idUser)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/tasks/")
                .requestAttr("idUser", idUser))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTaskById_NotFound() throws Exception {
        UUID taskId = UUID.randomUUID();
        when(taskRepository.findById(taskId)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/tasks/{id}", taskId))
                .andExpect(status().isNotFound());
    }
}