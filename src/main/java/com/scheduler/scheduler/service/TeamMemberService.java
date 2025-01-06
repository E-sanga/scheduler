package com.scheduler.scheduler.service;

import com.scheduler.scheduler.entity.TeamMember;
import com.scheduler.scheduler.repository.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TeamMemberService {

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Async
    @Transactional
    public void write(TeamMember teamMember) {

        teamMemberRepository.save(teamMember);
    }

    // 이미 팀이 있는 직원은 더이상 팀에 속할 수 없도록 필터링를 위한 함수
    public TeamMember filterView(Integer employeeId) {

        List<TeamMember> members = teamMemberRepository.findByEmployeeId(employeeId);
        return members.isEmpty() ? null : members.get(0);
    }

    // 팀에 속한 직원 리스트
    public List<TeamMember> memberList(Integer teamId){

        return teamMemberRepository.findByTeamId(teamId);
    }

    // 팀원 삭제
    @Async
    @Transactional
    public void deleteTeamMemberByMemberId(Integer memberId){
        teamMemberRepository.deleteByMemberId(memberId);
    }

    // 팀장을 팀원으로 변경
    @Async
    @Transactional
    public void roleChange(TeamMember teamMember){
        teamMemberRepository.save(teamMember);
    }

    // 직위 변경을 위한 팀원 정보 보기
    public TeamMember view(Integer memberId) {
        Optional<TeamMember> optionalTeamMember = teamMemberRepository.findById(memberId);
        return optionalTeamMember.orElse(null); // 존재하지 않는 경우 null 반환
    }

    // 직원 이름 변경시 팀원 이름 변경
    public void update(TeamMember teamMember) {
        teamMemberRepository.save(teamMember);
    }

}
