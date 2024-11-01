package com.scheduler.scheduler.service;

import com.scheduler.scheduler.entity.Employee;
import com.scheduler.scheduler.repository.EmployeeRopository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRopository employeeRopository;

    public void write(Employee employee) {

        employeeRopository.save(employee);

    }

    public List<Employee> employeeList() {

        return employeeRopository.findAll();
    }

    public void delete(Integer employee_id) {

        employeeRopository.deleteById(employee_id);
    }
}
