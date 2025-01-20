package br.com.iuryalmeida.TaskEase.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity(name = "tb_users")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    @NotBlank(message = "O nome de usuário é obrigatório")
    @Size(max = 50, message = "O nome de usuário deve ter menos de 50 caracteres")
    private String username;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome deve ter menos de 100 caracteres")
    private String name;

    @NotBlank(message = "A senha é obrigatória")
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;
}