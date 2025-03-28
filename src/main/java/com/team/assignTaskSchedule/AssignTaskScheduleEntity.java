package com.team.assignTaskSchedule;

import com.team.player.PlayerEntity;
import com.team.schedule.ScheduleEntity;
import com.team.task.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignTaskScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "player_id")
    private PlayerEntity player;

    @OneToOne
    @JoinColumn(name = "task_id")
    private TaskEntity task;

    @OneToOne
    @JoinColumn(name = "schedule_id")
    private ScheduleEntity schedule;

    private LocalDate startDate;
    private LocalDate endDate;

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

}
