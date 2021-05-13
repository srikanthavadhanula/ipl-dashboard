package com.srikanth.ipldashboard.controller;

import java.time.LocalDate;
import java.util.List;

import com.srikanth.ipldashboard.model.Match;
import com.srikanth.ipldashboard.model.Team;
import com.srikanth.ipldashboard.repository.MatchRepository;
import com.srikanth.ipldashboard.repository.TeamRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class TeamController {

    private TeamRepository teamRepository;
    private MatchRepository matchRepository;

    public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    @GetMapping(value = "team/{teamName}")
    public Team getTeams(@PathVariable String teamName) {
        Team team = teamRepository.findByTeamName(teamName);
        team.setTeamMatches(this.matchRepository.findLatestMatchesByTeam(teamName, 4));
        return team;
    }

    @GetMapping(value = "team/{teamName}/matches")
    public List<Match> getTheMatchList(@PathVariable String teamName, @RequestParam int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year+1, 1, 1);
        return this.matchRepository.GetAllMatchesByTeamName(teamName, startDate, endDate);
    }

}
