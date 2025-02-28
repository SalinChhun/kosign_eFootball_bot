package com.salin.kosign_eFootball_bot.payload.club;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface IGetClubResponse {

    @Value("#{target.id}")
    Long getClubId();

    @Value("#{target.name}")
    String getClubName();

    @Value("#{target.image}")
    String getClubLogo();

    @Value("#{target.seasons}")
    @JsonDeserialize(as = List.class, contentAs = IGetSeasonResponse.class)
    List<IGetSeasonResponse> getSeasons();

}
