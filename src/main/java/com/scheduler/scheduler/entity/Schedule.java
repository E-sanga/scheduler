package com.scheduler.scheduler.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_Id")
    private Integer scheduleId;

    private String emname;

    @Temporal(TemporalType.DATE)
    @Column(name = "closed_day")
    private Date closedDay;

    @Column(name = "employee_id")
    private Integer employeeId;

    // 휴무일을 직원 리스트로 불러가기 위한 처리
    @OneToOne(mappedBy = "schedule")
    private Employee employee;

}
