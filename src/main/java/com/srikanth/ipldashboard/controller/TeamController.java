package com.srikanth.ipldashboard.controller;

import com.srikanth.ipldashboard.model.Team;
import com.srikanth.ipldashboard.repository.MatchRepository;
import com.srikanth.ipldashboard.repository.TeamRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

}
