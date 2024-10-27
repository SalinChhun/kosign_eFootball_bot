package com.salin.kosign_eFootball_bot.payload;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamRanking {

    private Long teamId;
    private String teamName;
    private Integer points;
    private Integer totalGoals;
    private Integer rankPosition;

    @Builder
    public TeamRanking(Long teamId, String teamName, Integer points, Integer totalGoals, Integer rankPosition) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.points = points;
        this.totalGoals = totalGoals;
        this.rankPosition = rankPosition;
    }

}