package com.scheduler.scheduler.controller;

import com.scheduler.scheduler.entity.Schedule;
import com.scheduler.scheduler.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/close")
public class CloseController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/write")
    public ResponseEntity<?> writeClose(@RequestBody Map<String, String> payload) {
        Integer employeeId = Integer.valueOf(payload.get("employeeId"));
        String close = payload.get("closedDay");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Integer scheduleId = null;
        try {
            Date closedDay = dateFormat.parse(close);
            Schedule schedule = new Schedule();
            schedule.setEmployeeId(employeeId);
            schedule.setClosedDay(closedDay);
            scheduleId = scheduleService.write(schedule).get();
        } catch (ParseException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Map<String, Integer> response = new HashMap<>();
        response.put("scheduleId", scheduleId);
        return ResponseEntity.ok().body(Map.of("success", response));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteClose(@RequestBody Map<String, String> payload){
        Integer scheduleId = Integer.valueOf(payload.get("scheduleId"));
        scheduleService.delete(scheduleId);

        return ResponseEntity.ok().body(Map.of("success", 1));
    }
}
