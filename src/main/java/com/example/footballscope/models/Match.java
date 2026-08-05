package com.example.footballscope.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class Match {
    @Id
    private Long id;

    private LocalDateTime utcDate;

    private String status;

    private Integer homeScore;

    private Integer awayScore;

    @ManyToOne
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id")
    private Team awayTeam;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;
}
