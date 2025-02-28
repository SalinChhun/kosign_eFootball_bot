package com.salin.kosign_eFootball_bot.repository;

import com.salin.kosign_eFootball_bot.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Club, Long> {

    Optional<Club> findTeamByName(String name);
}
