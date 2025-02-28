package com.salin.kosign_eFootball_bot.repository;

import com.salin.kosign_eFootball_bot.domain.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRepository extends JpaRepository<Season, Long> {
}
