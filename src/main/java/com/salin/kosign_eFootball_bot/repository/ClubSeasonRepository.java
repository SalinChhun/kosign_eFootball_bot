package com.salin.kosign_eFootball_bot.repository;

import com.salin.kosign_eFootball_bot.domain.ClubSeason;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubSeasonRepository extends JpaRepository<ClubSeason, Long> {

    List<ClubSeason> findByClubId(Long clubId);
}
