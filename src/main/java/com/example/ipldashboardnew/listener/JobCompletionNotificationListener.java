package com.example.ipldashboardnew.listener;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Jdbc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.ipldashboardnew.model.Team;

import jakarta.persistence.EntityManager;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

  private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

  private final EntityManager em;

  @Autowired
  public JobCompletionNotificationListener(EntityManager em) {
    this.em = em;
  }

  @Override
  @Transactional
  public void afterJob(JobExecution jobExecution) {
    if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("!!! JOB FINISHED! Time to verify the results");
      // em.query("SELECT team1, team2,winning_team, date FROM match",
      //   (rs, row) -> "Team 1 " + rs.getString(1) + " Team 2 " + rs.getString(2) + " Winner " + rs.getString(3) + " Date " + rs.getString(4)
      // ).forEach(str -> System.out.println(str));
      Map<String,Team> teamData = new HashMap<>();
      em.createQuery("Select distinct m.team1, count(*) from Match m group by m.team1"  , Object[].class)
      .getResultList()
      .stream()
      .map(e -> new Team((String)e[0] , (long)e[1]))
      .forEach(team -> teamData.put(team.getTeamName(), team)); 

      em.createQuery("Select distinct m.team2, count(*) from Match m group by m.team2"  , Object[].class)
      .getResultList()
      .stream()
      .forEach(e -> {
         Team team = teamData.get((String)e[0]);
         team.setTotalMatches(team.getTotalMatches() + (long)e[1]);
        });
        
        em.createQuery("Select distinct m.winningTeam , count(*) from Match m group by m.winningTeam"  , Object[].class)
        .getResultList()
        .stream()
        .forEach(e -> {
           Team team = teamData.get((String)e[0]);
           if(team!=null){
            team.setTotalWins((long)e[1]);
           }
          });  
        teamData.values().forEach(team -> em.persist(team));
        teamData.values().forEach(team -> System.out.println(team));
    }
  }
}