package com.salin.kosign_eFootball_bot.services;


import com.salin.kosign_eFootball_bot.api.StatusCode;
import com.salin.kosign_eFootball_bot.domain.Match;
import com.salin.kosign_eFootball_bot.domain.Team;
import com.salin.kosign_eFootball_bot.exception.BusinessException;
import com.salin.kosign_eFootball_bot.exception.EntityNotFoundException;
import com.salin.kosign_eFootball_bot.payload.MatchRequest;
import com.salin.kosign_eFootball_bot.payload.MatchResponse;
import com.salin.kosign_eFootball_bot.repository.MatchRepository;
import com.salin.kosign_eFootball_bot.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    public void createMatch(MatchRequest request) {


        // Find home team
        Team homeTeam = teamRepository.findById(request.getHomeTeamId())
                .orElseThrow(() -> new EntityNotFoundException(Team.class, "id", request.getHomeTeamId().toString()));

        // Find away team
        Team awayTeam = teamRepository.findById(request.getAwayTeamId())
                .orElseThrow(() -> new EntityNotFoundException(Team.class, "id", request.getAwayTeamId().toString()));

        // Validate teams are different
        if (homeTeam.getId().equals(awayTeam.getId())) {
            throw new BusinessException(StatusCode.INVALID_TEAM_PAIR);
        }

        // Create new match
        Match match = new Match();
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);
        match.setMatchDate(LocalDateTime.now());
        match.setHomeScore(request.getHomeScore());
        match.setAwayScore(request.getAwayScore());

        // Save match
        matchRepository.save(match);

    }

}
