package com.team.schedule;

import com.team.task.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<ScheduleEntity> getSchedules() {
        return scheduleRepository.findAll();
    }

    public ScheduleEntity getById(Long id) {
        return scheduleRepository.findById(id).get();
    }

    public ScheduleEntity create(ScheduleEntity schedule) {
        return scheduleRepository.save(schedule);
    }
}
