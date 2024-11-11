package com.scheduler.scheduler.service;

import com.scheduler.scheduler.entity.Team;
import com.scheduler.scheduler.entity.TeamMember;
import com.scheduler.scheduler.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public void write(Team team) {

        teamRepository.save(team);

    }

    // 팀 생성 후, 팀원 추가를 위해 팀ID를 알아내기 위해 팀이름으로 팀넘버를 가져오는 처리
    public Team view(String teamName) {
        List<Team> teams = teamRepository.findByTeamName(teamName);
        return teams.isEmpty() ? null : teams.get(0);
    }

    public List<Team> teamList() {
        List<Team> teams = teamRepository.findAll();
        for (Team team : teams) {
            List<TeamMember> teamMembers = team.getTeamMembers();
            if (teamMembers != null) {
                for (TeamMember teamMember : teamMembers) {
                    if (teamMember.getTeam() == null) {
                        teamMember.setTeam(team);
                    }
                }
            }
        }
        return teams;
    }

    public void delete(Integer teamId) {

        teamRepository.deleteById(teamId);
    }

    // 수정을 위한 팀보기
    public Team updateView(Integer teamId) {
        Team team = teamRepository.findById(teamId).get();
        return team;
    }

    public Team update(Team team) {
        return teamRepository.save(team);
    }
}
