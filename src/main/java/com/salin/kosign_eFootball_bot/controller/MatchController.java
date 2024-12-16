package com.salin.kosign_eFootball_bot.controller;

import com.salin.kosign_eFootball_bot.payload.MatchRequest;
import com.salin.kosign_eFootball_bot.services.MatchResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/match")
@RequiredArgsConstructor
public class MatchController extends KosignEfootballBotResController{

    private final MatchResultService matchService;

    @PostMapping()
    public ResponseEntity<?> createMatch(@RequestBody MatchRequest matchRequest) {
//        matchService.createMatch(matchRequest);
        return ok();
    }

}
