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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/team/list")
    public String teamList(Model model) {
        List<Team> teams = teamService.teamList();
        for(Team team : teams){
            List<TeamMember> members = team.getTeamMembers();
            if(members != null) {
                members.removeIf(member -> member.getTeam() == null);
            }
        }
        model.addAttribute("list", teams);

        model.addAttribute("elist", employees());

        return "teamlist";
    }

    @PostMapping("/team/writepro")
    public String teamWritePro(Team team, @RequestParam String leader, @RequestParam String[] members) {
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
                teamMember.setTeamId(teamService.view(teamName).getTeamId());
                teamMember.setEmployeeId(selectedEmployeeId);
                teamMember.setRole("팀장");
                teamMember.setMember_name(selectedEmname);
                teamMemberService.write(teamMember);
            }
            // 팀원 생성
            for(String member : members){
                if(member != null){
                    String[] parts = member.split("\\|");
                    String selectedEmname = parts[0];
                    Integer selectedEmployeeId = Integer.valueOf(parts[1]);
                    // 초기화 해줌
                    TeamMember teamMember = new TeamMember();
                    teamMember.setTeamId(teamService.view(teamName).getTeamId());
                    teamMember.setEmployeeId(selectedEmployeeId);
                    teamMember.setRole("팀원");
                    teamMember.setMember_name(selectedEmname);
                    teamMemberService.write(teamMember);
                }
            }
        }

        return "redirect:/team/list";
    }


    @PostMapping("/team/delete")
    public String teamDelete(Integer teamId) {

        teamService.delete(teamId);
        return "redirect:/team/list";
    }

    @PostMapping("/team/update")
    public String teamUpdate(Integer teamId, Team team, @RequestParam(value = "leader", defaultValue = "") String leader) {
        // 팀원을 팀장으로 변경
        if(leader != null && !leader.isEmpty()){
            Integer memberId = Integer.valueOf(leader);
            TeamMember teamMember = teamMemberService.view(memberId);
            teamMember.setRole("팀장");
            teamMemberService.roleChange(teamMember);
        }
        team.setTeamId(teamId);
        teamService.update(team);
        return "redirect:/team/list";
    }

    // 팀에 속한 직원을 목록에서 제거하기 위한 처리
    public List<Employee> employees (){
        List<Employee> employeeCheck = employeeService.employeeList();
        List<Employee> employeesToRemove = new ArrayList<>();
        for(Employee employee : employeeCheck){
            Integer employeeId = employee.getEmployee_id();
            if(teamMemberService.filterView(employeeId) != null){
                employeesToRemove.add(employee);
            }
        }
        employeeCheck.removeAll(employeesToRemove);
        return employeeCheck;
    }
}
