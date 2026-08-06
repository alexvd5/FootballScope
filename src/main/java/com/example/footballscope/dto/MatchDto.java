package com.example.footballscope.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchDto {
    private Long id;
    private LocalDateTime utcDate;
    private String status;
    private TeamDto homeTeam;
    private TeamDto awayTeam;
    private ScoreDto score;
}
