package com.scheduler.scheduler.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer team_id;

    @Column(name = "team_name")
    private String teamName;

}
