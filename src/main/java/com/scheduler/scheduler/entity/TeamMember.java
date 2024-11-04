package com.scheduler.scheduler.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "team_member")
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer member_id;

    private Integer team_id;

    @Column(name = "employee_id")
    private Integer employeeId;

    private String role;

    private String member_name;

}
