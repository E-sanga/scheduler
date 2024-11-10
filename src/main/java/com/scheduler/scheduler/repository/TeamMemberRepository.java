package com.scheduler.scheduler.repository;

import com.scheduler.scheduler.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Integer> {
    List<TeamMember> findByEmployeeId(Integer employeeId);
    List<TeamMember> findByTeamId(Integer teamId);
    @Async
    @Transactional
    void deleteByMemberId(Integer memberId);
}
