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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamMemberService teamMemberService;

    @GetMapping("/schedule/list")
    public String scheduleList(Model model) {
        List<Team> teams = teamService.teamList();
        LinkedHashMap<String, List<TeamMember>> teamMemberMap = new LinkedHashMap<>();
        for (Team team : teams){
            Integer teamId = team.getTeamId();
            List<TeamMember> teamMembers = teamMemberService.memberList(teamId);
            teamMemberMap.put(team.getTeamName(), teamMembers);
        }

        model.addAttribute("teamMemberMap", teamMemberMap);
        model.addAttribute("sList", scheduleService.list());


        return "schedulelist";
    }


}
