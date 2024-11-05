package com.scheduler.scheduler.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Data
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer team_id;

    @Column(name = "team_name")
    private String teamName;

    @Getter
    @Setter
    @OneToMany(mappedBy = "team")
    private List<TeamMember> teamMembers;

}
