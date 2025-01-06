package com.scheduler.scheduler.service;

import com.scheduler.scheduler.entity.Schedule;
import com.scheduler.scheduler.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    public CompletableFuture<Integer> write(Schedule schedule){
        //scheduleRepository.save(schedule);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return CompletableFuture.completedFuture(savedSchedule.getScheduleId());
    }

    @Async
    @Transactional
    public void delete(Integer scheduleId){

        scheduleRepository.deleteById(scheduleId);
    }
}
