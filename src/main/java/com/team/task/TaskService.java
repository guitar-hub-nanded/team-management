package com.team.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<TaskEntity> getTasks() {
        return taskRepository.findAll();
    }

    public TaskEntity create(TaskEntity task) {
        return taskRepository.save(task);
    }

    public TaskEntity getById(Long id) {
        return taskRepository.findById(id).get();
    }
}
