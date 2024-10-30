package com.scheduler.scheduler.controller;

import com.scheduler.scheduler.entity.Employee;
import com.scheduler.scheduler.repository.EmployeeRopository;
import com.scheduler.scheduler.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;


@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;//

    @GetMapping("/employee/write")
    public String employeeWriteForm() {

        return "employeewrite";
    }

    @PostMapping("/employee/writepro")
    public String employeeWritePro(Employee employee, String joindate) {

        System.out.println(employee.getEmname());
        // String을 Date형식으로 변환
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            employee.setJoin_date(dateFormat.parse(joindate));
        } catch (ParseException e) {
            e.printStackTrace(); // 오류 발생 시 로그 출력
        }
        System.out.println(employee.getJoin_date());
        employeeService.write(employee);

        return "";
    }

}
