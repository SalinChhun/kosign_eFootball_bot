package com.salin.kosign_eFootball_bot.repository;

import com.salin.kosign_eFootball_bot.domain.MatchResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<MatchResult, Long> {

}
