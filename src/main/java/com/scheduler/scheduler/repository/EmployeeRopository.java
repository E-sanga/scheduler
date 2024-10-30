package com.scheduler.scheduler.repository;

import com.scheduler.scheduler.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRopository extends JpaRepository<Employee, Integer> {
}
