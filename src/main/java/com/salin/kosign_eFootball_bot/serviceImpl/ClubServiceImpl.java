package com.salin.kosign_eFootball_bot.serviceImpl;

import com.salin.kosign_eFootball_bot.domain.Club;
import com.salin.kosign_eFootball_bot.domain.ClubSeason;
import com.salin.kosign_eFootball_bot.domain.Season;
import com.salin.kosign_eFootball_bot.exception.EntityNotFoundException;
import com.salin.kosign_eFootball_bot.payload.club.*;
import com.salin.kosign_eFootball_bot.repository.ClubRepository;
import com.salin.kosign_eFootball_bot.repository.ClubSeasonRepository;
import com.salin.kosign_eFootball_bot.repository.SeasonRepository;
import com.salin.kosign_eFootball_bot.services.ClubService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final ClubSeasonRepository clubSeasonRepository;
    private final SeasonRepository seasonRepository;

    @Override
    @Transactional
    public void createClub(ClubRequest clubRequest) {

        clubRequest.getSeasonIds().forEach(seasonId -> {
            if (!seasonRepository.existsById(seasonId)) {
                throw new EntityNotFoundException(Season.class, "id", seasonId.toString());
            }
        });
        List<Season> seasons = seasonRepository.findAllById(clubRequest.getSeasonIds());

        Club club = clubRepository.save(
                Club.builder()
                        .name(clubRequest.getClubName())
                        .image(clubRequest.getClubLogo())
                        .build()
        );

        seasons.forEach(season -> {
            clubSeasonRepository.save(
                    ClubSeason.builder()
                            .club(club)
                            .season(season)
                            .build()
            );
        });
    }

    @Override
    public List<ClubResponse> getClubs(Long seasonId, String clubName) {
        try {
            // TODO: Get all clubs matching the filter criteria
            var iclubs = clubRepository.findAllClubsBySeasonId(seasonId, clubName);
            var clubs = iclubs.stream().map(club -> {

                // TODO: For each club, fetch its associated seasons
                var iseasons = clubRepository.findSeasonsByClubId(club.getClubId());
                var seasons = iseasons.stream().map(season->
                        SeasonResponse.builder()
                        .seasonId(season.getSeasonId())
                        .seasonName(season.getSeasonName())
                        .build()).toList();

                return ClubResponse.builder()
                        .id(club.getClubId())
                        .name(club.getClubName())
                        .image(club.getClubLogo())
                        .seasons(seasons)
                        .build();
            }).toList();

            return clubs;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public void updateClub(UpdateClubRequest updateClubRequest) {

        Club club = clubRepository.findById(updateClubRequest.getClubId())
                .orElseThrow(() -> new EntityNotFoundException(Club.class, "id", updateClubRequest.getClubId().toString()));


        // Validate all seasons exist
        updateClubRequest.getSeasonIds().forEach(seasonId -> {
            if (!seasonRepository.existsById(seasonId)) {
                throw new EntityNotFoundException(Season.class, "id", seasonId.toString());
            }
        });

        // Update club properties
        club.setName(updateClubRequest.getClubName());
        club.setImage(updateClubRequest.getClubLogo());
        clubRepository.save(club);

        // Handle season associations
        // First, get current seasons
        List<ClubSeason> existingClubSeasons = clubSeasonRepository.findByClubId(club.getId());
        List<Long> existingSeasonIds = existingClubSeasons.stream()
                .map(clubSeason -> clubSeason.getSeason().getId())
                .toList();


        // Remove seasons that are no longer needed
        existingClubSeasons.stream()
                .filter(clubSeason -> !updateClubRequest.getSeasonIds().contains(clubSeason.getSeason().getId()))
                .forEach(clubSeasonRepository::delete);

        // Add new seasons that weren't there before
        List<Season> seasons = seasonRepository.findAllById(updateClubRequest.getSeasonIds());
        seasons.stream()
                .filter(season -> !existingSeasonIds.contains(season.getId()))
                .forEach(season -> {
                    clubSeasonRepository.save(
                            ClubSeason.builder()
                                    .club(club)
                                    .season(season)
                                    .build()
                    );
                });


    }

    @Override
    public void deleteClub(Long clubId) {
        // Check if club exists
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new EntityNotFoundException(Club.class, "id", clubId.toString()));

        // Delete all associated ClubSeason records first
        List<ClubSeason> clubSeasons = clubSeasonRepository.findByClubId(clubId);
        clubSeasonRepository.deleteAll(clubSeasons);

        // Now delete the club
        clubRepository.delete(club);
    }
}
