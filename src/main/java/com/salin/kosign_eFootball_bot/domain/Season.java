package com.salin.kosign_eFootball_bot.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;  // e.g. "2024/25"

    @Builder
    public Season(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}