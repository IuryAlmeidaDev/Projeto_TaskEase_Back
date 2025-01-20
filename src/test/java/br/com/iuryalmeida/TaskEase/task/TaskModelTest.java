package br.com.iuryalmeida.TaskEase.task;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class TaskModelTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidTaskModel() {
        TaskModel taskModel = new TaskModel();
        taskModel.setDescription("Descrição da tarefa");
        taskModel.setTitle("Título da tarefa");
        taskModel.setStatus(TaskStatus.pendente);
        taskModel.setIdUser(UUID.randomUUID());

        Set<ConstraintViolation<TaskModel>> violations = validator.validate(taskModel);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidTaskModel_MissingDescription() {
        TaskModel taskModel = new TaskModel();
        taskModel.setTitle("Título da tarefa");
        taskModel.setStatus(TaskStatus.pendente);
        taskModel.setIdUser(UUID.randomUUID());

        Set<ConstraintViolation<TaskModel>> violations = validator.validate(taskModel);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidTaskModel_MissingTitle() {
        TaskModel taskModel = new TaskModel();
        taskModel.setDescription("Descrição da tarefa");
        taskModel.setStatus(TaskStatus.pendente);
        taskModel.setIdUser(UUID.randomUUID());

        Set<ConstraintViolation<TaskModel>> violations = validator.validate(taskModel);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidTaskModel_TitleTooLong() {
        TaskModel taskModel = new TaskModel();
        taskModel.setDescription("Descrição da tarefa");
        taskModel.setTitle("Título da tarefa que é muito longo e excede o limite de cinquenta caracteres");
        taskModel.setStatus(TaskStatus.pendente);
        taskModel.setIdUser(UUID.randomUUID());

        Set<ConstraintViolation<TaskModel>> violations = validator.validate(taskModel);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidTaskModel_MissingStatus() {
        TaskModel taskModel = new TaskModel();
        taskModel.setDescription("Descrição da tarefa");
        taskModel.setTitle("Título da tarefa");
        taskModel.setIdUser(UUID.randomUUID());

        Set<ConstraintViolation<TaskModel>> violations = validator.validate(taskModel);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidTaskModel_MissingIdUser() {
        TaskModel taskModel = new TaskModel();
        taskModel.setDescription("Descrição da tarefa");
        taskModel.setTitle("Título da tarefa");
        taskModel.setStatus(TaskStatus.pendente);

        Set<ConstraintViolation<TaskModel>> violations = validator.validate(taskModel);
        assertFalse(violations.isEmpty());
    }
}