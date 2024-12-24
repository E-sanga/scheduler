package com.scheduler.scheduler.service;

import com.scheduler.scheduler.entity.Employee;
import com.scheduler.scheduler.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void write(Employee employee) {

        employeeRepository.save(employee);

    }

    public List<Employee> employeeList() {

        return employeeRepository.findAll();
    }

    public void delete(Integer employee_id) {

        employeeRepository.deleteById(employee_id);
    }

    public void update(Employee employee) {
        Employee existingEmployee = employeeRepository.findById(employee.getEmployee_id()).orElse(null);
        if (existingEmployee != null) {
            existingEmployee.setEmname(employee.getEmname());
            existingEmployee.setClassify(employee.getClassify());
            existingEmployee.setJoin_date(employee.getJoin_date());
            // 필요한 다른 필드들도 동일하게 설정
            employeeRepository.save(existingEmployee);
        }

    }
}
