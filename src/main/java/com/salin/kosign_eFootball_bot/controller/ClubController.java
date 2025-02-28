package com.salin.kosign_eFootball_bot.controller;

import com.salin.kosign_eFootball_bot.payload.club.ClubRequest;
import com.salin.kosign_eFootball_bot.payload.club.UpdateClubRequest;
import com.salin.kosign_eFootball_bot.services.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class ClubController extends KosignEfootballBotResController {

    private final ClubService clubService;

    @PostMapping("/club")
    public ResponseEntity<?> createClub(@RequestBody ClubRequest clubRequest) {
        clubService.createClub(clubRequest);
        return ok();
    }

    @GetMapping("/clubs")
    public ResponseEntity<?> getClubs(
            @RequestParam(value = "season_id", required = false) Long seasonId,
            @RequestParam(value = "club_name", required = false) String clubName
    ) {
        return ok(clubService.getClubs(seasonId, clubName));
    }

    @PutMapping("/club")
    public ResponseEntity<?> updateClub(@RequestBody UpdateClubRequest updateClubRequest) {
        clubService.updateClub(updateClubRequest);
        return ok();
    }

    @DeleteMapping("/club/{clubId}")
    public ResponseEntity<?> deleteClub(@PathVariable Long clubId) {
        clubService.deleteClub(clubId);
        return ok();
    }
}
