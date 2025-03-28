package com.team.assignTaskSchedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class AssignTaskScheduleService {

    @Autowired
    private AssignTaskScheduleRepository assignTaskScheduleRepository;

    public List<AssignTaskScheduleEntity> getTaskSchedules() {
        return assignTaskScheduleRepository.findAll();
    }

    public AssignTaskScheduleEntity getById(Long id) {
        return assignTaskScheduleRepository.findById(id).get();
    }

    public AssignTaskScheduleEntity create(AssignTaskScheduleEntity assignTaskSchedule) {
        return assignTaskScheduleRepository.save(assignTaskSchedule);
    }

    public List<AssignTaskScheduleEntity> findByDateBetween(SearchDto searchDto) {
        LocalDate startDate = LocalDate.parse(searchDto.getStartDateStr());
        LocalDate endDate =LocalDate.parse(searchDto.getEndDateStr());
        if (startDate != null && endDate != null){
            return assignTaskScheduleRepository.findByStartDateBetween(startDate, endDate);
        }else{
            return assignTaskScheduleRepository.findAll();
        }
    }
}
