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

}
