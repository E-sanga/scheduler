package com.scheduler.scheduler.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employee_id;

    private String emname;

    private String classify;

    private Date join_date;

    // 휴무일을 가져오기 위한 처리
    @OneToOne
    @JoinColumn(name = "schedule_id") // Schedule의 외래 키를 참조
    private Schedule schedule;

    // Schedule의 closedDay를 가져오는 getter
    public Date getClosedDay() {
        return schedule != null ? schedule.getClosedDay() : null;
    }

}
