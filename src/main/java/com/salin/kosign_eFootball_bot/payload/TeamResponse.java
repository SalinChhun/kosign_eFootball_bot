package com.salin.kosign_eFootball_bot.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamResponse {

    private String homeTeam;
    private String awayTeam;

    @Builder
    public TeamResponse(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }
}
