package com.filipegome.todoapp.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.filipegome.todoapp.interfaces.IUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = UserModel.TABLE_NAME)
public class UserModel {

    public static final String TABLE_NAME = "app_user";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(name = "username", length = 15, nullable = false, unique = true)
    @NotNull(groups = IUser.createUser.class)
    @NotEmpty(groups = IUser.createUser.class)
    @Size(groups = IUser.createUser.class, min = 2, max = 15)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", length = 16, nullable = false)
    @NotNull(groups = {IUser.createUser.class, IUser.updateUser.class})
    @NotEmpty(groups = {IUser.createUser.class, IUser.updateUser.class})
    @Size(groups = {IUser.createUser.class, IUser.updateUser.class}, min = 8, max = 16)
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    private List<TaskModel> tasks = new ArrayList<TaskModel>();

}

