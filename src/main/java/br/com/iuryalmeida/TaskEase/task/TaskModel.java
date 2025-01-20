package br.com.iuryalmeida.TaskEase.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity(name="tb_tasks")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @NotBlank(message = "A descrição é obrigatória")
    private String description;

    @Column(length = 50)
    @NotBlank(message = "O título é obrigatório")
    @Size(max = 50, message = "O título deve ter menos de 50 caracteres")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "O status é obrigatório")
    private TaskStatus status = TaskStatus.pendente;

    @NotNull(message = "O ID do usuário é obrigatório")
    private UUID idUser;

    private LocalDateTime startAt;
    private LocalDateTime endAt;

    @CreationTimestamp
    private LocalDateTime createdAt;
}