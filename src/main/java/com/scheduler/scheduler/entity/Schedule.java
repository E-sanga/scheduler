package com.scheduler.scheduler.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer schedule_Id;

    private String emname;

    @Temporal(TemporalType.DATE)
    @Column(name = "closed_day")
    private Date closed_day;

    // 휴무일을 직원 리스트로 불러가기 위한 처리
    @OneToOne(mappedBy = "schedule")
    private Employee employee;

    public Date getClosedDay() {
        return closed_day;
    }

    public void setClosedDay(Date closed_day) {
        this.closed_day = closed_day;
    }

}
