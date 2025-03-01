package com.salin.kosign_eFootball_bot.services;

import com.salin.kosign_eFootball_bot.payload.club.*;

import java.util.List;

public interface ClubService {

    void createClub(ClubRequest clubRequest);

    List<ClubResponse> getClubs(Long seasonId, String clubName);

    void updateClub(UpdateClubRequest updateClubRequest);

    void deleteClub(Long clubId);
}
