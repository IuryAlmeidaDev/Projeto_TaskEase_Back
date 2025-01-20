package br.com.iuryalmeida.TaskEase.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.iuryalmeida.TaskEase.Utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Task Controller", description = "APIs para gerenciamento de tarefas")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    private boolean isInvalidDates(TaskModel taskModel) {
        var currentDateTime = LocalDateTime.now();
        return currentDateTime.isAfter(taskModel.getEndAt()) || 
               (taskModel.getStartAt() != null && taskModel.getStartAt().isAfter(taskModel.getEndAt()));
    }

    @PostMapping("/")
    @Operation(summary = "Cria uma nova tarefa", description = "Cria uma nova tarefa para o usuário autenticado")
    public ResponseEntity<?> create(@Valid @RequestBody TaskModel taskModel, HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);
        
        if (isInvalidDates(taskModel)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de término (endAt) deve ser maior que a data atual e a data de início (startAt) deve ser antes da data de término (endAt).");
        }

        taskModel.setStatus(TaskStatus.pendente);
        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping("/")
    @Operation(summary = "Obtém todas as tarefas", description = "Obtém todas as tarefas do usuário autenticado")
    public ResponseEntity<?> getTasks(HttpServletRequest request) {
        var idUser = (UUID) request.getAttribute("idUser");
        List<TaskModel> tasks = this.taskRepository.findByIdUser(idUser);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Obtém tarefas por status", description = "Obtém todas as tarefas do usuário autenticado com o status especificado")
    public ResponseEntity<?> getTasksByStatus(@PathVariable TaskStatus status, HttpServletRequest request) {
        var idUser = (UUID) request.getAttribute("idUser");
        List<TaskModel> tasks = this.taskRepository.findByStatusAndIdUser(status, idUser);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/due/{dueDate}")
    @Operation(summary = "Obtém tarefas por data de vencimento", description = "Obtém todas as tarefas do usuário autenticado com a data de vencimento especificada")
    public ResponseEntity<?> getTasksByDueDate(@PathVariable String dueDate, HttpServletRequest request) {
        var idUser = (UUID) request.getAttribute("idUser");
        LocalDateTime dueDateTime = LocalDateTime.parse(dueDate);
        List<TaskModel> tasks = this.taskRepository.findByEndAtAndIdUser(dueDateTime, idUser);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtém tarefa por ID", description = "Obtém a tarefa do usuário autenticado com o ID especificado")
    public ResponseEntity<?> getTaskById(@PathVariable UUID id, HttpServletRequest request) {
        var task = this.taskRepository.findById(id).orElse(null);

        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada");
        }

        var idUser = request.getAttribute("idUser");
        if (!task.getIdUser().equals(idUser)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Usuário não tem permissão para acessar essa tarefa");
        }

        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma tarefa", description = "Atualiza a tarefa do usuário autenticado com o ID especificado")
    public ResponseEntity<?> update(@RequestBody TaskModel taskModel, @PathVariable UUID id, HttpServletRequest request) {
        var task = this.taskRepository.findById(id).orElse(null);
    
        if (task == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa não encontrada");
        }
    
        var idUser = request.getAttribute("idUser");
    
        if (!task.getIdUser().equals(idUser)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Usuário não tem permissão para alterar essa tarefa");
        }
    
        Utils.copyNonNullProperties(taskModel, task);
        var taskUpdated = this.taskRepository.save(task);
        return ResponseEntity.ok().body(taskUpdated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma tarefa", description = "Exclui a tarefa do usuário autenticado com o ID especificado")
    public ResponseEntity<?> delete(@PathVariable UUID id, HttpServletRequest request) {
        try {
            var task = this.taskRepository.findById(id).orElse(null);

            if (task == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada");
            }

            var idUser = request.getAttribute("idUser");
            if (!task.getIdUser().equals(idUser)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Usuário não tem permissão para excluir essa tarefa");
            }

            this.taskRepository.delete(task);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao excluir a tarefa: " + e.getMessage());
        }
    }
}