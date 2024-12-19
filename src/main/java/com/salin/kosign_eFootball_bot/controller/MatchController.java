package com.salin.kosign_eFootball_bot.controller;

import com.salin.kosign_eFootball_bot.payload.MatchRequest;
import com.salin.kosign_eFootball_bot.services.MatchResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/test")
    public ResponseEntity<?> getMatch() {
        return ok("test");
    }

}
