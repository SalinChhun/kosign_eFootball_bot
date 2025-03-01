package com.salin.kosign_eFootball_bot.payload.club;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SeasonResponse {

    private Long seasonId;
    private String seasonName;

    @Builder
    public SeasonResponse(Long seasonId, String seasonName) {
        this.seasonId = seasonId;
        this.seasonName = seasonName;
    }
}
