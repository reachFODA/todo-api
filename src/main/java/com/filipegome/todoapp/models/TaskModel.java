package com.filipegome.todoapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = TaskModel.TASK_TABLE)
public class TaskModel {

    public static final String TASK_TABLE = "app_task";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @Column(name = "description", length = 256, nullable = false, updatable = false)
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 256)
    private String description;

}
