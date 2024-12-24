package com.scheduler.scheduler.service;

import com.scheduler.scheduler.entity.Schedule;
import com.scheduler.scheduler.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Schedule> list(){

        return scheduleRepository.findAll();
    }

    public List<Schedule> filterList(Integer employeeId) {
        if (employeeId != null) {
            return scheduleRepository.findByEmployeeId(employeeId);
        } else {
            return Collections.emptyList();
        }
    }

    @Async
    @Transactional
    public void write(Schedule schedule){

        scheduleRepository.save(schedule);
    }

    @Async
    @Transactional
    public void delete(Integer scheduleId){

        scheduleRepository.deleteById(scheduleId);
    }
}
