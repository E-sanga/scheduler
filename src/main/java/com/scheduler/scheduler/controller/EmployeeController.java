package com.scheduler.scheduler.controller;

import com.scheduler.scheduler.entity.Employee;
import com.scheduler.scheduler.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;


@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee/write")
    public String employeeWriteForm() {

        return "employeewrite";
    }

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

        model.addAttribute("list", employeeService.employeeList());
        // System.out.println(model);

        return "employeelist";
    }

    @PostMapping("/employee/delete")
    public String employeeDelete(Integer employee_id) {

        employeeService.delete(employee_id);
        return "redirect:/employee/list";
    }

    @GetMapping("/employee/modify/{employee_id}")
    public String employeeModify(@PathVariable("employee_id") Integer employee_id, Model model) {

        model.addAttribute("employee", employeeService.view(employee_id));
        return "employeemodify";
    }

    @PostMapping("/employee/update/{employee_id}")
    public String employeeUpdate(@PathVariable("employee_id") Integer employee_id, Employee employee, String joindate) {

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
