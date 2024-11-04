package com.scheduler.scheduler.repository;

import com.scheduler.scheduler.entity.Team;
import com.scheduler.scheduler.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Integer> {
    List<TeamMember> findByEmployeeId(Integer employeeId);
}
