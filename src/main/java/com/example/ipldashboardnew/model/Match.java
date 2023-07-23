package com.example.ipldashboardnew.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Match {
    @Id
    private Long id;
    private String city;
    private LocalDate date ;
    private String matchNumber;
    private String team1;
    private String team2;
    private String venue;
    private String tossWinner;
    private String tossDecision;
    private String superOver;
    private String winningTeam;
    private String wonBy;
    private String margin;
    private String playerOfMatch;
    private String umpire1;
    private String umpire2;
    @Override
    public String toString() {
        return "Match [id=" + id + ", city=" + city + ", date=" + date + ", matchNumber=" + matchNumber + ", team1="
                + team1 + ", team2=" + team2 + ", venue=" + venue + ", tossWinner=" + tossWinner + ", tossDecision="
                + tossDecision + ", superOver=" + superOver + ", winningTeam=" + winningTeam + ", wonBy=" + wonBy
                + ", margin=" + margin + ", playerOfMatch=" + playerOfMatch + ", umpire1=" + umpire1 + ", umpire2="
                + umpire2 + "]";
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public String getMatchNumber() {
        return matchNumber;
    }
    public void setMatchNumber(String matchNumber) {
        this.matchNumber = matchNumber;
    }
    public String getTeam1() {
        return team1;
    }
    public void setTeam1(String team1) {
        this.team1 = team1;
    }
    public String getTeam2() {
        return team2;
    }
    public void setTeam2(String team2) {
        this.team2 = team2;
    }
    public String getVenue() {
        return venue;
    }
    public void setVenue(String venue) {
        this.venue = venue;
    }
    public String getTossWinner() {
        return tossWinner;
    }
    public void setTossWinner(String tossWinner) {
        this.tossWinner = tossWinner;
    }
    public String getTossDecision() {
        return tossDecision;
    }
    public void setTossDecision(String tossDecision) {
        this.tossDecision = tossDecision;
    }
    public String getSuperOver() {
        return superOver;
    }
    public void setSuperOver(String superOver) {
        this.superOver = superOver;
    }
    public String getWinningTeam() {
        return winningTeam;
    }
    public void setWinningTeam(String winningTeam) {
        this.winningTeam = winningTeam;
    }
    public String getWonBy() {
        return wonBy;
    }
    public void setWonBy(String wonBy) {
        this.wonBy = wonBy;
    }
    public String getMargin() {
        return margin;
    }
    public void setMargin(String margin) {
        this.margin = margin;
    }
    public String getPlayerOfMatch() {
        return playerOfMatch;
    }
    public void setPlayerOfMatch(String playerOfMatch) {
        this.playerOfMatch = playerOfMatch;
    }
    public String getUmpire1() {
        return umpire1;
    }
    public void setUmpire1(String umpire1) {
        this.umpire1 = umpire1;
    }
    public String getUmpire2() {
        return umpire2;
    }
    public void setUmpire2(String umpire2) {
        this.umpire2 = umpire2;
    }
}
