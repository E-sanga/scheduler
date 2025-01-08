package com.scheduler.scheduler.controller;

import com.scheduler.scheduler.entity.Employee;
import com.scheduler.scheduler.entity.Schedule;
import com.scheduler.scheduler.entity.TeamMember;
import com.scheduler.scheduler.service.EmployeeService;
import com.scheduler.scheduler.service.ScheduleService;
import com.scheduler.scheduler.service.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private TeamMemberService teamMemberService;

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
    public String employeeList(Model model, @RequestParam(value = "search", defaultValue = "") String search) {

        if (search != null && !search.isEmpty()) {
            List<Employee> employees = employeeService.search(search);
            // 리스트 존재 여부에 따라 차등 처리
            if (employees != null && !employees.isEmpty()) {
                model.addAttribute("list", employeesCloseDay(employees));
            } else {
                model.addAttribute("list", employees);
            }
        } else {
            List<Employee> employees = employeeService.employeeList();
            // 리스트 존재 여부에 따라 차등 처리
            if (employees != null && !employees.isEmpty()) {
                model.addAttribute("list", employeesCloseDay(employees));
            } else {
                model.addAttribute("list", employees);
            }
        }

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
        TeamMember teamMember = teamMemberService.filterView(employee_id);
        if (teamMember != null) {
            teamMember.setMember_name(employee.getEmname());
            teamMemberService.update(teamMember);
        }

        return "redirect:/employee/list";
    }

    // 직원별 휴무일 처리를 위한 코드
    public List<Employee> employeesCloseDay (List<Employee> employees){
        LocalDate now = LocalDate.now();
        String nowYear = String.valueOf(now.getYear());
        String nowMonth = String.format("%02d", now.getMonthValue());
        // 스케줄 날짜 저장을 위한 생성
        StringBuilder sb = new StringBuilder();
        for (Employee employee : employees) {
            Integer employeeId = employee.getEmployee_id();
            List<Schedule> employeeSchedule = scheduleService.filterList(employeeId);

            if (employeeSchedule != null && !employeeSchedule.isEmpty()) {
                // StringBuilder 초기화
                sb.setLength(0);
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
        return employees;
    }
}
