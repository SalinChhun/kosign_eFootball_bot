package com.salin.kosign_eFootball_bot.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class MatchResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @ManyToOne
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id")
    private Team awayTeam;

    private LocalDateTime matchDate;

    @Column(nullable = false)
    private Integer homeScore = 0;

    @Column(nullable = false)
    private Integer awayScore = 0;

    @Column(nullable = false)
    private String homePossession;

    @Column(nullable = false)
    private String awayPossession;

    @Column(nullable = false)
    private Integer homeShots = 0;

    @Column(nullable = false)
    private Integer awayShots = 0;

    @Column(nullable = false)
    private Integer homeShotsOnTarget = 0;

    @Column(nullable = false)
    private Integer awayShotsOnTarget = 0;

    @Column(nullable = false)
    private Integer homeFouls = 0;

    @Column(nullable = false)
    private Integer awayFouls = 0;

    @Column(nullable = false)
    private Integer homeOffsides = 0;

    @Column(nullable = false)
    private Integer awayOffsides = 0;

    @Column(nullable = false)
    private Integer homeCornerKicks = 0;

    @Column(nullable = false)
    private Integer awayCornerKicks = 0;

    @Column(nullable = false)
    private Integer homeFreeKicks = 0;

    @Column(nullable = false)
    private Integer awayFreeKicks = 0;

    @Column(nullable = false)
    private Integer homePasses = 0;

    @Column(nullable = false)
    private Integer awayPasses = 0;

    @Column(nullable = false)
    private Integer homeSuccessfulPasses = 0;

    @Column(nullable = false)
    private Integer awaySuccessfulPasses = 0;

    @Column(nullable = false)
    private Integer homeCrosses = 0;

    @Column(nullable = false)
    private Integer awayCrosses = 0;

    @Column(nullable = false)
    private Integer homeInterceptions = 0;

    @Column(nullable = false)
    private Integer awayInterceptions = 0;

    @Column(nullable = false)
    private Integer homeTackles = 0;

    @Column(nullable = false)
    private Integer awayTackles = 0;

    @Column(nullable = false)
    private Integer homeSaves = 0;

    @Column(nullable = false)
    private Integer awaySaves = 0;

    @Builder
    public MatchResult(Long id, Team homeTeam, Team awayTeam, LocalDateTime matchDate, Integer homeScore, Integer awayScore, String homePossession, String awayPossession, Integer homeShots, Integer awayShots, Integer homeShotsOnTarget, Integer awayShotsOnTarget, Integer homeFouls, Integer awayFouls, Integer homeOffsides, Integer awayOffsides, Integer homeCornerKicks, Integer awayCornerKicks, Integer homeFreeKicks, Integer awayFreeKicks, Integer homePasses, Integer awayPasses, Integer homeSuccessfulPasses, Integer awaySuccessfulPasses, Integer homeCrosses, Integer awayCrosses, Integer homeInterceptions, Integer awayInterceptions, Integer homeTackles, Integer awayTackles, Integer homeSaves, Integer awaySaves) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.matchDate = matchDate;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.homePossession = homePossession;
        this.awayPossession = awayPossession;
        this.homeShots = homeShots;
        this.awayShots = awayShots;
        this.homeShotsOnTarget = homeShotsOnTarget;
        this.awayShotsOnTarget = awayShotsOnTarget;
        this.homeFouls = homeFouls;
        this.awayFouls = awayFouls;
        this.homeOffsides = homeOffsides;
        this.awayOffsides = awayOffsides;
        this.homeCornerKicks = homeCornerKicks;
        this.awayCornerKicks = awayCornerKicks;
        this.homeFreeKicks = homeFreeKicks;
        this.awayFreeKicks = awayFreeKicks;
        this.homePasses = homePasses;
        this.awayPasses = awayPasses;
        this.homeSuccessfulPasses = homeSuccessfulPasses;
        this.awaySuccessfulPasses = awaySuccessfulPasses;
        this.homeCrosses = homeCrosses;
        this.awayCrosses = awayCrosses;
        this.homeInterceptions = homeInterceptions;
        this.awayInterceptions = awayInterceptions;
        this.homeTackles = homeTackles;
        this.awayTackles = awayTackles;
        this.homeSaves = homeSaves;
        this.awaySaves = awaySaves;
    }

    public MatchResult() {

    }
}
