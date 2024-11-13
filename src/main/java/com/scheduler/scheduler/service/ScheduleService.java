package com.scheduler.scheduler.service;

import com.scheduler.scheduler.entity.Schedule;
import com.scheduler.scheduler.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Schedule> list(){
        return scheduleRepository.findAll();
    }
}
