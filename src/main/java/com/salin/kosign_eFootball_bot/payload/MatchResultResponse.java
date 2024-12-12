package com.salin.kosign_eFootball_bot.payload;


import com.salin.kosign_eFootball_bot.domain.Team;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class MatchResultResponse {

    private String homeTeam = "";
    
    private String awayTeam = "";
    
    private LocalDateTime matchDate;
    
    private String homeScore = "";
    
    private String awayScore = "";
    
    private String homePossession;
    
    private String awayPossession;
    
    private String homeShots = "";
    
    private String awayShots = "";
    
    private String homeShotsOnTarget = "";
    
    private String awayShotsOnTarget = "";
    
    private String homeFouls = "";
    
    private String awayFouls = "";
    
    private String homeOffsides = "";
    
    private String awayOffsides = "";
    
    private String homeCornerKicks = "";
    
    private String awayCornerKicks = "";
    
    private String homeFreeKicks = "";
    
    private String awayFreeKicks = "";
    
    private String homePasses = "";
    
    private String awayPasses = "";
    
    private String homeSuccessfulPasses = "";
    
    private String awaySuccessfulPasses = "";
    
    private String homeCrosses = "";
    
    private String awayCrosses = "";
    
    private String homeInterceptions = "";
    
    private String awayInterceptions = "";
    
    private String homeTackles = "";
    
    private String awayTackles = "";
    
    private String homeSaves = "";
    
    private String awaySaves = "";

    @Builder
    public MatchResultResponse(String homeTeam, String awayTeam, LocalDateTime matchDate, String homeScore, String awayScore, String homePossession, String awayPossession, String homeShots, String awayShots, String homeShotsOnTarget, String awayShotsOnTarget, String homeFouls, String awayFouls, String homeOffsides, String awayOffsides, String homeCornerKicks, String awayCornerKicks, String homeFreeKicks, String awayFreeKicks, String homePasses, String awayPasses, String homeSuccessfulPasses, String awaySuccessfulPasses, String homeCrosses, String awayCrosses, String homeInterceptions, String awayInterceptions, String homeTackles, String awayTackles, String homeSaves, String awaySaves) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.matchDate = matchDate;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.homePossession = homePossession;
        this.awayPossession = awayPossession;
        this.homeShots = homeShots;
        this.awayShots = awayShots;
        this.homeShotsOnTarget = homeShotsOnTarget;
        this.awayShotsOnTarget = awayShotsOnTarget;
        this.homeFouls = homeFouls;
        this.awayFouls = awayFouls;
        this.homeOffsides = homeOffsides;
        this.awayOffsides = awayOffsides;
        this.homeCornerKicks = homeCornerKicks;
        this.awayCornerKicks = awayCornerKicks;
        this.homeFreeKicks = homeFreeKicks;
        this.awayFreeKicks = awayFreeKicks;
        this.homePasses = homePasses;
        this.awayPasses = awayPasses;
        this.homeSuccessfulPasses = homeSuccessfulPasses;
        this.awaySuccessfulPasses = awaySuccessfulPasses;
        this.homeCrosses = homeCrosses;
        this.awayCrosses = awayCrosses;
        this.homeInterceptions = homeInterceptions;
        this.awayInterceptions = awayInterceptions;
        this.homeTackles = homeTackles;
        this.awayTackles = awayTackles;
        this.homeSaves = homeSaves;
        this.awaySaves = awaySaves;
    }
}