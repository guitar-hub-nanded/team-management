package com.team.assignTaskSchedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AssignTaskScheduleRepository extends JpaRepository<AssignTaskScheduleEntity, Long> {
    List<AssignTaskScheduleEntity> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

}
