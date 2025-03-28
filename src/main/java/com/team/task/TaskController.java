package com.team.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/get-tasks")
    private String getTasks(Model model){
        model.addAttribute("tasks", taskService.getTasks());
        return "Tasks";
    }

    @GetMapping("/add")
    public String createTaskForm(Model model){
        TaskEntity task = new TaskEntity();
        model.addAttribute("task", task);
        return "AddTask";
    }

    @GetMapping("/edit/{id}")
    public String editTaskForm(@PathVariable("id") Long id, Model model){
        TaskEntity task = taskService.getById(id);
        model.addAttribute("task", task);
        return "EditTask";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute("task") TaskEntity task ){
        task.setCreatedOn(LocalDateTime.now());
        TaskEntity taskEntity = taskService.create(task);
        return "redirect:/task/get-tasks";
    }

    @PostMapping("/update/{id}")
    public String createTask(@PathVariable("id") Long id, @ModelAttribute("task") TaskEntity task ){
        TaskEntity existingTask = taskService.getById(id);
        existingTask.setName(task.getName());
        existingTask.setDescription(task.getDescription());
        existingTask.setUpdatedOn(LocalDateTime.now());
        TaskEntity taskEntity = taskService.create(task);
        return "redirect:/task/get-tasks";
    }

}
