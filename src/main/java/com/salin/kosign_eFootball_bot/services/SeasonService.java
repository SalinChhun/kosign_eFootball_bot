package com.salin.kosign_eFootball_bot.services;

import com.salin.kosign_eFootball_bot.domain.Season;

import java.util.List;

public interface SeasonService {

    public void createSeason(String seasonName);

    public List<Season> getSeasons();

    public void updateSeason(Long seasonId, String seasonName);

    public void deleteSeason(Long seasonId);
}
