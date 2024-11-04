package com.scheduler.scheduler.service;

import com.scheduler.scheduler.entity.Team;
import com.scheduler.scheduler.entity.TeamMember;
import com.scheduler.scheduler.repository.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamMemberService {

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    public void write(TeamMember teamMember) {

        teamMemberRepository.save(teamMember);

    }

    // 이미 팀이 있는 직원은 더이상 팀에 속할 수 없도록 필터링를 위한 함수
    public TeamMember filterView(Integer employeeId) {
        List<TeamMember> members = teamMemberRepository.findByEmployeeId(employeeId);
        return members.isEmpty() ? null : members.get(0);
    }
}
