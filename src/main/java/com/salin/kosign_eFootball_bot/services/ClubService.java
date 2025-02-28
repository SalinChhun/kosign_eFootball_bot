package com.salin.kosign_eFootball_bot.services;

import com.salin.kosign_eFootball_bot.payload.club.ClubRequest;
import com.salin.kosign_eFootball_bot.payload.club.IGetClubResponse;
import com.salin.kosign_eFootball_bot.payload.club.UpdateClubRequest;

import java.util.List;

public interface ClubService {

    public void createClub(ClubRequest clubRequest);

    public List<IGetClubResponse> getClubs(Long seasonId, String clubName);

    public void updateClub(UpdateClubRequest updateClubRequest);

    public void deleteClub(Long clubId);
}
