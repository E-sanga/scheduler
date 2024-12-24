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

    private String closeDay;

}
