package com.scheduler.scheduler.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "team_member")
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "team_id")
    private Integer teamId;

    @Column(name = "employee_id")
    private Integer employeeId;

    private String role;

    private String member_name;

    @ManyToOne
    @JoinColumn(name = "team_id", insertable = false, updatable = false)
    private Team team;

    public Integer GetTeamId() {
        return teamId;
    }

    public void SetTeamId(Integer team_id) {
        this.teamId = team_id;
    }

    public Integer GetEmployeeId() {
        return employeeId;
    }

    public String GetMemberName() {
        return member_name;
    }

    public void SetMemberName(String member_name) {
        this.member_name = member_name;
    }

    public Team GetTeam() {
        return team;
    }

    public void SetTeam(Team team) {
        this.team = team;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

}
