package com.salin.kosign_eFootball_bot.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RankingResponse {

    private List<TeamRanking> rankings;

    @Builder
    public RankingResponse(List<TeamRanking> rankings) {
        this.rankings = rankings;
    }
}
