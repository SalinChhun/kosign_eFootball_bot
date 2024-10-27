package com.salin.kosign_eFootball_bot.payload;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MatchResponse {
    private Long id;
    private Long homeTeamId;
    private String homeTeamName;
    private Long awayTeamId;
    private String awayTeamName;
    private Date matchDate;
    private Integer homeScore;
    private Integer awayScore;

    @Builder
    public MatchResponse(Long id, Long homeTeamId, String homeTeamName, Long awayTeamId, String awayTeamName, Date matchDate, Integer homeScore, Integer awayScore) {
        this.id = id;
        this.homeTeamId = homeTeamId;
        this.homeTeamName = homeTeamName;
        this.awayTeamId = awayTeamId;
        this.awayTeamName = awayTeamName;
        this.matchDate = matchDate;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }
}