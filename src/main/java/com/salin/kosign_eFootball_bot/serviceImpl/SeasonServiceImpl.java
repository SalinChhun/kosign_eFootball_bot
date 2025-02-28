package com.salin.kosign_eFootball_bot.serviceImpl;

import com.salin.kosign_eFootball_bot.domain.Season;
import com.salin.kosign_eFootball_bot.exception.EntityNotFoundException;
import com.salin.kosign_eFootball_bot.repository.SeasonRepository;
import com.salin.kosign_eFootball_bot.services.SeasonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeasonServiceImpl implements SeasonService {

    private final SeasonRepository seasonRepository;

    @Override
    public void createSeason(String seasonName) {
        seasonRepository.save(
                Season.builder()
                        .name(seasonName)
                        .build()
        );
    }

    @Override
    public List<Season> getSeasons() {
        return seasonRepository.findAll();
    }

    @Override
    public void updateSeason(Long seasonId, String seasonName) {
        Season season = seasonRepository.findById(seasonId).orElseThrow(() -> new EntityNotFoundException(Season.class, "id", seasonId.toString()));
        season.setName(seasonName);
        seasonRepository.save(season);
    }

    @Override
    public void deleteSeason(Long seasonId) {
        Season season = seasonRepository.findById(seasonId).orElseThrow(() -> new EntityNotFoundException(Season.class, "id", seasonId.toString()));
        seasonRepository.delete(season);
    }
}
