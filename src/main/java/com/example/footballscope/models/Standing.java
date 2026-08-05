package com.example.footballscope.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Standing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int possition;

    private int playedMatches;

    private int won;

    private int draw;

    private int lost;

    private int points;

    private int goalsFor;

    private int goalsAgainst;

    private int goalDifference;

    @ManyToOne
    private Team team;

    @ManyToOne
    private League league;
}
