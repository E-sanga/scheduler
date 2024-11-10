package com.scheduler.scheduler.controller;

import com.scheduler.scheduler.entity.TeamMember;
import com.scheduler.scheduler.service.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private TeamMemberService teamMemberService;

    @PostMapping("/delete")
    public ResponseEntity<?> deleteMember(@RequestBody Map<String, Integer> payload){

        Integer memberId = payload.get("id");
        if(payload.get("role") == 1){
            TeamMember teamMember = teamMemberService.view(memberId);
            teamMember.setRole("팀원");
            teamMemberService.roleChange(teamMember);
        } else {
            teamMemberService.deleteTeamMemberByMemberId(memberId);
        }
        return ResponseEntity.ok().body(Map.of("success", 1));
    }
}
