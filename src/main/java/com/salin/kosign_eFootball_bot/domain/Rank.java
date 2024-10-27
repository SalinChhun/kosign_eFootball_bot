package com.salin.kosign_eFootball_bot.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rank")
@Getter
@Setter
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_id", nullable = false)
    private Long teamId;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @Column(nullable = false)
    private Integer points;

    @Column(name = "rank_position", nullable = false)
    private Integer rankPosition;

    @Column(name = "total_goals", nullable = false)  // New field
    private Integer totalGoals;

}