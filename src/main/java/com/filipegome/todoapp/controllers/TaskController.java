package com.filipegome.todoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.filipegome.todoapp.models.TaskModel;
import com.filipegome.todoapp.services.TaskService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {
    
    @Autowired
    private TaskService taskService;
    
    @GetMapping("/{id}")
    public ResponseEntity<TaskModel> findById(@PathVariable Long id) {
        TaskModel obj = taskService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    @Validated
    public ResponseEntity<TaskModel> create(@Valid @RequestBody TaskModel obj) {
        TaskModel newObj = taskService.create(obj);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri()).build();
    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<TaskModel> update(@Valid @RequestBody TaskModel obj, @PathVariable Long id) {
        obj.setId(id);
        this.taskService.update(obj);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<TaskModel>> findAllByUser(@PathVariable Long id) {
        List<TaskModel> tasks = this.taskService.findAllByUser(id);
        return ResponseEntity.ok().body(tasks);
    }

}
