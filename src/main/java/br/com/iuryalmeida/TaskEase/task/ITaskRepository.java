package br.com.iuryalmeida.TaskEase.task;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    List<TaskModel> findByStatusAndIdUser(TaskStatus status, UUID idUser);
    List<TaskModel> findByIdUser(UUID idUser);
}