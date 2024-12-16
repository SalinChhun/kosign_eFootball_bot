package com.salin.kosign_eFootball_bot.services;


import com.salin.kosign_eFootball_bot.api.StatusCode;
import com.salin.kosign_eFootball_bot.domain.MatchResult;
import com.salin.kosign_eFootball_bot.domain.Team;
import com.salin.kosign_eFootball_bot.exception.BusinessException;
import com.salin.kosign_eFootball_bot.exception.EntityNotFoundException;
import com.salin.kosign_eFootball_bot.payload.MatchRequest;
import com.salin.kosign_eFootball_bot.payload.MatchResultResponse;
import com.salin.kosign_eFootball_bot.repository.MatchResultRepository;
import com.salin.kosign_eFootball_bot.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchResultService {

    private final MatchResultRepository matchResultRepository;
    private final TeamRepository teamRepository;

    public boolean createMatchResult(MatchResultResponse matchResultRequest) {


        // Find home team
        Team homeTeam = teamRepository.findTeamByName(matchResultRequest.getHomeTeam())
                .orElseThrow(() -> new EntityNotFoundException(Team.class, "name", matchResultRequest.getHomeTeam()));

        // Find away team
        Team awayTeam = teamRepository.findTeamByName(matchResultRequest.getAwayTeam())
                .orElseThrow(() -> new EntityNotFoundException(Team.class, "name", matchResultRequest.getAwayTeam()));

        // Validate teams are different
        if (homeTeam.getId().equals(awayTeam.getId())) {
            throw new BusinessException(StatusCode.INVALID_TEAM_PAIR);
        }

        // Check if match result already exists


        // Save match result
        matchResultRepository.save(
                MatchResult.builder()
                        .homeTeam(homeTeam)
                        .awayTeam(awayTeam)
                        .matchDate(LocalDateTime.now())
                        .homeScore(Integer.parseInt(matchResultRequest.getHomeScore()))
                        .awayScore(Integer.parseInt(matchResultRequest.getAwayScore()))
                        .homePossession(matchResultRequest.getHomePossession())
                        .awayPossession(matchResultRequest.getAwayPossession())
                        .homeShots(Integer.parseInt(matchResultRequest.getHomeShots()))
                        .awayShots(Integer.parseInt(matchResultRequest.getAwayShots()))
                        .homeShotsOnTarget(Integer.parseInt(matchResultRequest.getHomeShotsOnTarget()))
                        .awayShotsOnTarget(Integer.parseInt(matchResultRequest.getAwayShotsOnTarget()))
                        .homeFouls(Integer.parseInt(matchResultRequest.getHomeFouls()))
                        .awayFouls(Integer.parseInt(matchResultRequest.getAwayFouls()))
                        .homeOffsides(Integer.parseInt(matchResultRequest.getHomeOffsides()))
                        .awayOffsides(Integer.parseInt(matchResultRequest.getAwayOffsides()))
                        .homeCornerKicks(Integer.parseInt(matchResultRequest.getHomeCornerKicks()))
                        .awayCornerKicks(Integer.parseInt(matchResultRequest.getAwayCornerKicks()))
                        .homeFreeKicks(Integer.parseInt(matchResultRequest.getHomeFreeKicks()))
                        .awayFreeKicks(Integer.parseInt(matchResultRequest.getAwayFreeKicks()))
                        .homePasses(Integer.parseInt(matchResultRequest.getHomePasses()))
                        .awayPasses(Integer.parseInt(matchResultRequest.getAwayPasses()))
                        .homeSuccessfulPasses(Integer.parseInt(matchResultRequest.getHomeSuccessfulPasses()))
                        .awaySuccessfulPasses(Integer.parseInt(matchResultRequest.getAwaySuccessfulPasses()))
                        .homeCrosses(Integer.parseInt(matchResultRequest.getHomeCrosses()))
                        .awayCrosses(Integer.parseInt(matchResultRequest.getAwayCrosses()))
                        .homeInterceptions(Integer.parseInt(matchResultRequest.getHomeInterceptions()))
                        .awayInterceptions(Integer.parseInt(matchResultRequest.getAwayInterceptions()))
                        .homeTackles(Integer.parseInt(matchResultRequest.getHomeTackles()))
                        .awayTackles(Integer.parseInt(matchResultRequest.getAwayTackles()))
                        .homeSaves(Integer.parseInt(matchResultRequest.getHomeSaves()))
                        .awaySaves(Integer.parseInt(matchResultRequest.getAwaySaves()))
                        .build()
        );

        return true;

    }

}
