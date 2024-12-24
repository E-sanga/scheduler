package com.scheduler.scheduler.controller;

import com.scheduler.scheduler.entity.Employee;
import com.scheduler.scheduler.entity.Schedule;
import com.scheduler.scheduler.service.EmployeeService;
import com.scheduler.scheduler.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;


@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/employee/writepro")
    public String employeeWritePro(Employee employee, String joindate) {

        // String을 Date형식으로 변환
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            employee.setJoin_date(dateFormat.parse(joindate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        employeeService.write(employee);

        return "redirect:/employee/list";
    }

    @GetMapping("/employee/list")
    public String employeeList(Model model) {

        List<Employee> employees = employeeService.employeeList();

        LocalDate now = LocalDate.now();
        String nowYear = String.valueOf(now.getYear());
        String nowMonth = String.valueOf(now.getMonthValue());
        for (Employee employee : employees) {
            Integer employeeId = employee.getEmployee_id();
            List<Schedule> employeeSchedule = scheduleService.filterList(employeeId);

            // 스케줄 날짜 저장을 위한 생성
            StringBuilder sb = new StringBuilder();
            if (employeeSchedule != null && !employeeSchedule.isEmpty()) {
                for (Schedule schedule : employeeSchedule) {
                    String scheduleDay = String.valueOf(schedule.getClosedDay());
                    String[] scheduleDayData = scheduleDay.split("-");

                    String year = scheduleDayData[0];
                    String month = scheduleDayData[1];
                    String day = scheduleDayData[2];

                    if (year.equals(nowYear) && month.equals(nowMonth)) {
                        if (!sb.isEmpty()) {
                            sb.append(",");
                        }
                        sb.append(day);
                    }
                }
                String scheduleList = sb.toString();
                employee.setCloseDay(scheduleList);
            } else {
                employee.setCloseDay("없음");
            }
        }

        model.addAttribute("list", employees);

        return "employeelist";
    }

    @PostMapping("/employee/delete")
    public String employeeDelete(Integer employee_id) {

        employeeService.delete(employee_id);
        return "redirect:/employee/list";
    }

    @PostMapping("/employee/update")
    public String employeeUpdateTwo(Integer employee_id, Employee employee, String joindate) {

        // String을 Date형식으로 변환
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            employee.setJoin_date(dateFormat.parse(joindate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        employeeService.update(employee);

        return "redirect:/employee/list";
    }

}
