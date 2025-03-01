package com.salin.kosign_eFootball_bot.payload.club;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClubResponse {

    private Long id;
    private String name;
    private String image;
    private List<SeasonResponse> seasons;

    @Builder
    public ClubResponse(Long id, String name, String image, List<SeasonResponse> seasons) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.seasons = seasons;
    }
}
