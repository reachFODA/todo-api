package com.filipegome.todoapp.services;

import com.filipegome.todoapp.models.UserModel;
import com.filipegome.todoapp.repositories.TaskRepository;
import com.filipegome.todoapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;

    public UserModel findById(Long id) {
        Optional<UserModel> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id + "Tipo:" + UserModel.class.getName()));
    }

    @Transactional
    public UserModel create(UserModel obj) {
        obj.setId(null);
        obj = this.userRepository.save(obj);
        this.taskRepository.saveAll(obj.getTasks());
        return obj;
    }

    @Transactional
    public UserModel update(UserModel obj) {
        UserModel newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        return this.userRepository.save(newObj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível deletar o usuário com o ID: " + id + "Tipo:" + UserModel.class.getName());
        }
    }

    
}