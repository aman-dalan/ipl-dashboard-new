package com.example.ipldashboardnew.processor;

import java.time.LocalDate;
import org.springframework.batch.item.ItemProcessor;

import com.example.ipldashboardnew.data.MatchInput;
import com.example.ipldashboardnew.model.Match;

public class MatchProcessor implements ItemProcessor<MatchInput, Match> {

  @Override
  public Match process(final MatchInput matchInput) throws Exception {
    Match match = new Match();
    match.setId(Long.parseLong(matchInput.getId()));
    match.setCity(matchInput.getCity());
    match.setDate(LocalDate.parse(matchInput.getDate()));
    match.setMatchNumber(matchInput.getMatchNumber());
    match.setVenue(matchInput.getVenue());
    match.setTossWinner(matchInput.getTossWinner());
    match.setTossDecision(matchInput.getTossDecision());
    match.setTeam1(matchInput.getTeam1());
    match.setTeam2(matchInput.getTeam2());
    match.setSuperOver(matchInput.getSuperOver());
    match.setWinningTeam(matchInput.getWinningTeam());
    match.setWonBy(matchInput.getWonBy());
    match.setMargin(matchInput.getMargin());
    match.setPlayerOfMatch(matchInput.getPlayerOfMatch());
    match.setUmpire1(matchInput.getUmpire1());
    match.setUmpire2(matchInput.getUmpire2());


    // team1 : firstInningsTeam , team2 : secondInningsTeam
    String firstInningsTeam , secondInningsTeam;
    
    if("bat".equals(matchInput.getTossDecision())){
      firstInningsTeam = matchInput.getTossWinner();  
      secondInningsTeam = matchInput.getTossWinner().equals(matchInput.getTeam1()) ? matchInput.getTeam2() : matchInput.getTeam1();    
    }
    else {
       secondInningsTeam = matchInput.getTossWinner();
       firstInningsTeam = matchInput.getTossWinner().equals(matchInput.getTeam1()) ? matchInput.getTeam2() : matchInput.getTeam1();
    }
    match.setTeam1(firstInningsTeam);
    match.setTeam2(secondInningsTeam);
    return match;
  }

}