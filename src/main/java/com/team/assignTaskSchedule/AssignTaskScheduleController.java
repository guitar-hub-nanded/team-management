package com.team.assignTaskSchedule;

import com.team.player.PlayerService;
import com.team.schedule.ScheduleEntity;
import com.team.schedule.ScheduleService;
import com.team.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AssignTaskScheduleController {

    @Autowired
    private AssignTaskScheduleService assignTaskScheduleService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public String getAssignTaskSchedule(Model model){
        model.addAttribute("assignTaskSchedules", assignTaskScheduleService.getTaskSchedules());
        model.addAttribute("searchDto", new SearchDto());
        return "AssignTaskSchedule";
    }

    @GetMapping("/add")
    public String createAssignTaskScheduleForm(Model model){
        model.addAttribute("players", playerService.getPlayers());
        model.addAttribute("schedules", scheduleService.getSchedules());
        model.addAttribute("tasks", taskService.getTasks());
        model.addAttribute("assignTaskSchedule", new AssignTaskScheduleEntity());
        return "AddAssignTaskSchedule";
    }

    @GetMapping("/edit/{id}")
    public String editAssignTaskScheduleForm(@PathVariable("id") Long id, Model model){
        model.addAttribute("players", playerService.getPlayers());
        model.addAttribute("schedules", scheduleService.getSchedules());
        model.addAttribute("tasks", taskService.getTasks());
        model.addAttribute("assignTaskSchedule", assignTaskScheduleService.getById(id));
        return "EditAssignTaskSchedule";
    }

    @PostMapping("/create")
    public String createAssignTaskSchedule(@ModelAttribute("assignTaskSchedule") AssignTaskScheduleEntity assignTaskSchedule ){
        assignTaskSchedule.setStartDate(assignTaskSchedule.getSchedule().getStartDate());
        assignTaskSchedule.setEndDate(assignTaskSchedule.getSchedule().getEndDate());
        assignTaskSchedule.setCreatedOn(LocalDateTime.now());
        AssignTaskScheduleEntity taskEntity = assignTaskScheduleService.create(assignTaskSchedule);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String createAssignTaskSchedule(@PathVariable("id") Long id, @ModelAttribute("assignTaskSchedule") AssignTaskScheduleEntity assignTaskSchedule ){
        AssignTaskScheduleEntity existingAssignTaskSchedule = assignTaskScheduleService.getById(id);
        existingAssignTaskSchedule.setTask(assignTaskSchedule.getTask());
        existingAssignTaskSchedule.setPlayer(assignTaskSchedule.getPlayer());
        existingAssignTaskSchedule.setSchedule(assignTaskSchedule.getSchedule());
        existingAssignTaskSchedule.setStartDate(assignTaskSchedule.getSchedule().getStartDate());
        existingAssignTaskSchedule.setEndDate(assignTaskSchedule.getSchedule().getEndDate());
        existingAssignTaskSchedule.setUpdatedOn(LocalDateTime.now());
        AssignTaskScheduleEntity assignTaskScheduleEntity = assignTaskScheduleService.create(existingAssignTaskSchedule);
        return "redirect:/";
    }

    @PostMapping("/search")
    public String searchSchedule(@ModelAttribute("searchDto") SearchDto searchDto, Model model ){
        model.addAttribute("assignTaskSchedules", assignTaskScheduleService.findByDateBetween(searchDto));
        model.addAttribute("searchDto", searchDto);
        return "AssignTaskSchedule";
    }

}
