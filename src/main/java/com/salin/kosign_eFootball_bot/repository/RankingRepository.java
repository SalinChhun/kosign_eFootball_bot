package com.salin.kosign_eFootball_bot.repository;

import com.salin.kosign_eFootball_bot.domain.Rank;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RankingRepository extends JpaRepository<Rank, Long> {


//    @Query("select r from Rank r order by r.rankPosition")
//    List<Rank> findByOrderByRankPositionAsc();
}
