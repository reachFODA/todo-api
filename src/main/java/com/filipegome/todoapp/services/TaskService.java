package com.filipegome.todoapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filipegome.todoapp.models.TaskModel;
import com.filipegome.todoapp.models.UserModel;
import com.filipegome.todoapp.repositories.TaskRepository;

import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserService userService;

    public TaskModel findById(Long id) {
        Optional<TaskModel> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new RuntimeException("Tarefa não encontrada com o ID: " + id + "Tipo:" + TaskModel.class.getName()));
    }

    @Transactional
    public TaskModel create(TaskModel obj) {
        UserModel user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public TaskModel update(TaskModel obj) {
        TaskModel newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar tarefa com o ID: " + id + "Tipo:" + TaskModel.class.getName());
        }
    }

    public List<TaskModel> findAllByUser(Long id) {
        List<TaskModel> tasks = this.taskRepository.findByUser_Id(id);
        return tasks;
    }

}
