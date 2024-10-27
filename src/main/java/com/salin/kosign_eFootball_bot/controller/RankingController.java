package com.salin.kosign_eFootball_bot.controller;

import com.salin.kosign_eFootball_bot.services.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/rank")
public class RankingController extends KosignEfootballBotResController{


    private final RankingService rankingService;

    @GetMapping("/getAll-ranks")
    public ResponseEntity<?> getTeamsRanking() {

        var ranking = rankingService.getTeamRanking();
        return ok(ranking);
    }
}
