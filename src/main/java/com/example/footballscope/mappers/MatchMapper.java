package com.example.footballscope.mappers;

import com.example.footballscope.dto.FullTimeDto;
import com.example.footballscope.dto.MatchDto;
import com.example.footballscope.dto.ScoreDto;
import com.example.footballscope.dto.TeamDto;
import com.example.footballscope.models.Match;

import java.util.List;

public class MatchMapper {

    public MatchDto toDto(Match match) {
        MatchDto dto = new MatchDto();
        dto.setId(match.getId());
        dto.setStatus(match.getStatus());
        dto.setUtcDate(match.getUtcDate());

        if (match.getHomeTeam() != null) {
            TeamDto homeTeam = new TeamDto();
            homeTeam.setId(match.getHomeTeam().getId());
            homeTeam.setName(match.getHomeTeam().getName());
            homeTeam.setCrest(match.getHomeTeam().getCrest());
            dto.setHomeTeam(homeTeam);
        }

        if (match.getAwayTeam() != null) {
            TeamDto awayTeam = new TeamDto();
            awayTeam.setId(match.getAwayTeam().getId());
            awayTeam.setName(match.getAwayTeam().getName());
            awayTeam.setCrest(match.getAwayTeam().getCrest());
            dto.setAwayTeam(awayTeam);
        }

        if (match.getHomeScore() != null || match.getAwayScore() != null) {
            FullTimeDto fullTime = new FullTimeDto();
            fullTime.setHome(match.getHomeScore());
            fullTime.setAway(match.getAwayScore());

            ScoreDto score = new ScoreDto();
            score.setFullTime(fullTime);

            dto.setScore(score);
        }

        return dto;
    }

    public List<MatchDto> toDtoList(List<Match> matches) {
        return matches.stream()
                .map(this::toDto)
                .toList();
    }
}
