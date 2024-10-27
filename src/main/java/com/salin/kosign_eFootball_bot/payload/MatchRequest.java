package com.salin.kosign_eFootball_bot.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@JsonInclude
public class MatchRequest {

    private Long homeTeamId;
    private Long awayTeamId;
    private Integer homeScore;
    private Integer awayScore;

    @Builder
    public MatchRequest(Long homeTeamId, Long awayTeamId, Integer homeScore, Integer awayScore) {
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }
}
