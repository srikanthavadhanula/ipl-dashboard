package com.srikanth.ipldashboard.repository;

import java.util.List;

import com.srikanth.ipldashboard.model.Match;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface MatchRepository extends CrudRepository<Match, Long> {
    List<Match> findByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable page);

    default List<Match> findLatestMatchesByTeam(String teamName, int count){
        return findByTeam1OrTeam2OrderByDateDesc(teamName, teamName, PageRequest.of(0, count));
    }
}
