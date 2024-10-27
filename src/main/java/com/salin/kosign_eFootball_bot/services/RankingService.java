package com.salin.kosign_eFootball_bot.services;

import com.salin.kosign_eFootball_bot.domain.Match;
import com.salin.kosign_eFootball_bot.domain.Rank;
import com.salin.kosign_eFootball_bot.domain.Team;
import com.salin.kosign_eFootball_bot.payload.RankingResponse;
import com.salin.kosign_eFootball_bot.payload.TeamRanking;
import com.salin.kosign_eFootball_bot.repository.MatchRepository;
import com.salin.kosign_eFootball_bot.repository.RankingRepository;
import com.salin.kosign_eFootball_bot.repository.TeamRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingService {
    
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final RankingRepository rankingRepository;

    public RankingResponse getTeamRanking() {

        List<Rank> ranks = rankingRepository.findAll();

        return RankingResponse
                .builder()
                .rankings(
                        ranks.stream().map(rank -> {
                            return TeamRanking.builder()
                                    .rankPosition(rank.getRankPosition())
                                    .teamId(rank.getTeamId())
                                    .teamName(rank.getTeamName())
                                    .points(rank.getPoints())
                                    .totalGoals(rank.getTotalGoals())
                                    .build();
                        }).toList()
                )
                .build();

    }

}
