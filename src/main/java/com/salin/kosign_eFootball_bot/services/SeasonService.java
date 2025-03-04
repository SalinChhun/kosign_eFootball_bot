package com.salin.kosign_eFootball_bot.services;

import com.salin.kosign_eFootball_bot.domain.Season;

import java.util.List;

public interface SeasonService {

    void createSeason(String seasonName);

    List<Season> getSeasons();

    void updateSeason(Long seasonId, String seasonName);

    void deleteSeason(Long seasonId);
}
