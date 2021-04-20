package com.srikanth.ipldashboard.data;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.srikanth.ipldashboard.model.Team;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final EntityManager em;

    @Autowired
    public JobCompletionNotificationListener(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            // we need to store all the team instances of a specific team
            Map<String, Team> teamData = new HashMap<>();

            // fetching all the teams(in team1) with their total match and storing in teamData
            em.createQuery("select m.team1 , count(*) from Match m group by m.team1", Object[].class)
            .getResultList()
            .stream()
            .map(e -> new Team((String)e[0] , (long)e[1]))
            .forEach(team -> teamData.put(team.getTeamName(), team));


            // fetching all the teams(in team2) with their total match and updating in teamData
            em.createQuery("select m.team2 , count(*) from Match m group by m.team2", Object[].class)
            .getResultList()
            .stream()
            .forEach(e -> {
                Team team = teamData.get((String) e[0]);
                team.setTotalMatches(team.getTotalMatches() + (long) e[1]);
            });

            // fetching all the teams with their total wins and storing in teamData
            em.createQuery("select m.matchWinner , count(*) from Match m group by m.matchWinner", Object[].class)
            .getResultList()
            .stream()
            .forEach(e -> {
                Team team = teamData.get((String) e[0]);
                if (team != null) team.setTotalWins((long) e[1]);
            });

            // storing the data in the teams table
            teamData.values().forEach(team -> em.persist(team));
            System.out.println("Team Name ,Total MatchesPlayed , TeamMatchesWon");
            teamData.values().forEach(team -> System.out.println(team.getTeamName() +" , "+team.getTotalMatches() + " , " + team.getTotalWins()));
        }
    }
}