package com.team.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/get-schedules")
    public String getSchedule(Model model){
        model.addAttribute("schedules",scheduleService.getSchedules());
        return "Schedules";
    }

    @GetMapping("/add")
    public String createScheduleForm(Model model){
        ScheduleEntity schedule = new ScheduleEntity();
        model.addAttribute("schedule", schedule);
        return "AddSchedule";
    }

    @GetMapping("/edit/{id}")
    public String editScheduleForm(@PathVariable("id") Long id, Model model){
        ScheduleEntity schedule = scheduleService.getById(id);
        model.addAttribute("schedule", schedule);
        return "EditSchedule";
    }

    @PostMapping("/create")
    public String createSchedule(@ModelAttribute("schedule") ScheduleEntity schedule ){
        schedule.setStartDate(LocalDate.parse(schedule.getStartDateStr()));
        schedule.setEndDate(LocalDate.parse(schedule.getEndDateStr()));
        schedule.setCreatedOn(LocalDateTime.now());
        ScheduleEntity scheduleEntity = scheduleService.create(schedule);
        return "redirect:/schedule/get-schedules";
    }

    @PostMapping("/update/{id}")
    public String updateSchedule(@PathVariable("id") Long id, @ModelAttribute("schedule") ScheduleEntity schedule ){
        ScheduleEntity existingSchedule = scheduleService.getById(id);
        existingSchedule.setName(schedule.getName());
        existingSchedule.setDescription(schedule.getDescription());
        existingSchedule.setStartDate(schedule.getStartDate());
        existingSchedule.setEndDate(schedule.getEndDate());
        existingSchedule.setStartDate(LocalDate.parse(schedule.getStartDateStr()));
        existingSchedule.setEndDate(LocalDate.parse(schedule.getEndDateStr()));
        existingSchedule.setUpdatedOn(LocalDateTime.now());
        ScheduleEntity scheduleEntity = scheduleService.create(schedule);
        return "redirect:/schedule/get-schedules";
    }

}
