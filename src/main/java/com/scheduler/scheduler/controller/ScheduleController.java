package com.scheduler.scheduler.controller;

import com.scheduler.scheduler.entity.Team;
import com.scheduler.scheduler.entity.TeamMember;
import com.scheduler.scheduler.service.ScheduleService;
import com.scheduler.scheduler.service.TeamMemberService;
import com.scheduler.scheduler.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;

@Controller
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamMemberService teamMemberService;

    @GetMapping("/schedule/list")
    public String scheduleList(Model model, @RequestParam(value = "teamValue", defaultValue = "") String teamValue) {
        Integer teamId;
        List<Team> teams = teamService.teamList();
        LinkedHashMap<String, List<TeamMember>> teamMemberMap = new LinkedHashMap<>();
        if (teamValue != null && !teamValue.isEmpty()) {
            teamId = Integer.valueOf(teamValue);
            List<TeamMember> teamMembers = teamMemberService.memberList(teamId);
            Team team = teamService.updateView(teamId);
            teamMemberMap.put(team.getTeamName(), teamMembers);
            model.addAttribute("teamMemberMap", teamMemberMap);
        } else {
            for (Team team : teams){
                teamId = team.getTeamId();
                List<TeamMember> teamMembers = teamMemberService.memberList(teamId);
                teamMemberMap.put(team.getTeamName(), teamMembers);
            }
            model.addAttribute("teamMemberMap", teamMemberMap);
        }
        // 팀별로 보기를 위한 처리
        model.addAttribute("teams", teams);
        model.addAttribute("sList", scheduleService.list());


        return "schedulelist";
    }


}
