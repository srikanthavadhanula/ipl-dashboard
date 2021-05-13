package com.srikanth.ipldashboard.repository;

import java.time.LocalDate;
import java.util.List;

import com.srikanth.ipldashboard.model.Match;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MatchRepository extends CrudRepository<Match, Long> {
    List<Match> findByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable page);


    @Query("select m from Match m where (m.team1 = :teamName or m.team2 = :teamName) and date between :startDate and :endDate order by date desc")
    List<Match> getAllMatches(
        @Param("teamName") String teamName, 
        @Param("startDate") LocalDate startDate, 
        @Param("endDate") LocalDate endDate
        );

    default List<Match> GetAllMatchesByTeamName(String teamName, LocalDate startDate, LocalDate endDate){
        return getAllMatches(teamName, startDate, endDate);
    }

    default List<Match> findLatestMatchesByTeam(String teamName, int count){
        return findByTeam1OrTeam2OrderByDateDesc(teamName, teamName, PageRequest.of(0, count));
    }
}
