package com.salin.kosign_eFootball_bot.repository;

import com.salin.kosign_eFootball_bot.domain.Club;
import com.salin.kosign_eFootball_bot.payload.club.IGetClubResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {

    @Query(value = """
    SELECT
        c.id,
        c.name,
        c.image,
        (
            SELECT json_agg(
                json_build_object(
                    'season_id', s.id,
                    'season_name', s.name
                )
            )
            FROM club_season cs
            JOIN season s ON cs.season_id = s.id
            WHERE cs.club_id = c.id
            GROUP BY cs.club_id
       ) as seasons
    FROM club c
    LEFT JOIN club_season cs on c.id = cs.club_id
    WHERE ((?1 IS NULL) OR cs.season_id IN (?1))
    AND (COALESCE(?2, '') = '' OR c.name ilike CONCAT('%', ?2, '%'))
    GROUP BY c.id
    """, nativeQuery = true)
    List<IGetClubResponse> findAllBySeasonId(Long seasonId, String clubName);


}
