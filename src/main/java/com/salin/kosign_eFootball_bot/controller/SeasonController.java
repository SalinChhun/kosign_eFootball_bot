package com.salin.kosign_eFootball_bot.controller;


import com.salin.kosign_eFootball_bot.services.SeasonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class SeasonController extends KosignEfootballBotResController{

    private final SeasonService seasonService;

    @PostMapping("/season")
    public ResponseEntity<?> createSeason(@RequestParam(value = "season_name", required = false) String seasonName) {
        seasonService.createSeason(seasonName);
        return ok();
    }

    @GetMapping("/seasons")
    public ResponseEntity<?> getSeasons() {
        return ok(seasonService.getSeasons());
    }

    @PutMapping("/season/{seasonId}")
    public ResponseEntity<?> updateSeason(
            @PathVariable Long seasonId,
            @RequestParam(value = "season_name", required = false) String seasonName
    ) {
        seasonService.updateSeason(seasonId, seasonName);
        return ok();
    }

    @DeleteMapping("/season/{seasonId}")
    public ResponseEntity<?> deleteSeason(
            @PathVariable Long seasonId
    ) {
        seasonService.deleteSeason(seasonId);
        return ok();
    }

}
