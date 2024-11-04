package com.scheduler.scheduler.controller;

import com.scheduler.scheduler.entity.Employee;
import com.scheduler.scheduler.entity.TeamMember;
import com.scheduler.scheduler.service.EmployeeService;
import com.scheduler.scheduler.service.TeamMemberService;
import org.springframework.ui.Model;
import com.scheduler.scheduler.entity.Team;
import com.scheduler.scheduler.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TeamMemberService teamMemberService;

    @GetMapping("/team/write")
    public String teamWrite(Model model) {
        List<Employee> employees = employeeService.employeeList();
        List<Employee> employeesToRemove = new ArrayList<>();
        // 팀에 속한 직원을 목록에서 제거하기 위한 처리
        for (Employee employee : employees) {
            Integer employeeId = employee.getEmployee_id();
            if(teamMemberService.filterView(employeeId) != null){
                employeesToRemove.add(employee);
            }
        }
        // 팀에 속한 직원을 목록에서 제거
        employees.removeAll(employeesToRemove);
        model.addAttribute("elist", employees);
        return "teamwrite";
    }

    @PostMapping("/team/writepro")
    public String teamWritePro(Team team, @RequestParam String leader, @RequestParam String member) {
        teamService.write(team);
        String teamName = team.getTeamName();
        // 방금 생성된 팀명으로 팀번호 가져오기
        if (teamName != null) {
            teamService.view(teamName);
            // 팀장 생성
            if(leader != null){
                String[] parts = leader.split("\\|");
                String selectedEmname = parts[0];
                Integer selectedEmployeeId = Integer.valueOf(parts[1]);
                // 초기화 해줌
                TeamMember teamMember = new TeamMember();
                teamMember.setTeam_id(teamService.view(teamName).getTeam_id());
                teamMember.setEmployeeId(selectedEmployeeId);
                teamMember.setRole("팀장");
                teamMember.setMember_name(selectedEmname);
                teamMemberService.write(teamMember);
            }
            // 팀원 생성
            if(member != null){
                String[] parts = member.split("\\|");
                String selectedEmname = parts[0];
                Integer selectedEmployeeId = Integer.valueOf(parts[1]);
                // 초기화 해줌
                TeamMember teamMember = new TeamMember();
                teamMember.setTeam_id(teamService.view(teamName).getTeam_id());
                teamMember.setEmployeeId(selectedEmployeeId);
                teamMember.setRole("팀원");
                teamMember.setMember_name(selectedEmname);
                teamMemberService.write(teamMember);
            }
        }

        return "redirect:/team/list";
    }

    @GetMapping("/team/list")
    public String teamList(Model model) {

        model.addAttribute("list", teamService.teamList());

        return "teamlist";
    }
}
