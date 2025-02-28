package com.salin.kosign_eFootball_bot.payload.club;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.beans.factory.annotation.Value;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface IGetSeasonResponse {

    @Value("#{target.season_id}")
    Long getSeasonId();

    @Value("#{target.season_name}")
    String getSeasonName();
}
