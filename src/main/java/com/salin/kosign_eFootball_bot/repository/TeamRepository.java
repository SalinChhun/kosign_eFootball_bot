package com.salin.kosign_eFootball_bot.repository;

import com.salin.kosign_eFootball_bot.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findTeamByName(String name);
}
