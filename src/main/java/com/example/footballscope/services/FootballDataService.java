package com.example.footballscope.services;

import com.example.footballscope.dto.*;
import com.example.footballscope.models.Match;
import com.example.footballscope.models.Team;
import com.example.footballscope.repositories.MatchRepository;
import com.example.footballscope.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class FootballDataService {

    @Value("${football.api.token}")
    private String apiToken;

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    public FootballDataService(MatchRepository matchRepository, TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
    }

    public MatchResponseDto getMatchesFromApi() {
        RestClient restClient = RestClient.create();

        return restClient.get()
                .uri("https://api.football-data.org/v4/competitions/CL/matches")
                .header("X-Auth-Token", apiToken)
                .retrieve()
                .body(MatchResponseDto.class);
    }

    @Transactional
    public void syncMatches() {
        MatchResponseDto response = getMatchesFromApi();

        if (response != null && response.getMatches() != null) {
            for (MatchDto dto : response.getMatches()) {

                Team homeTeam = teamRepository.findById(dto.getHomeTeam().getId())
                        .orElseGet(() -> {
                            Team newTeam = new Team();
                            newTeam.setId(dto.getHomeTeam().getId());
                            newTeam.setName(dto.getHomeTeam().getName());
                            newTeam.setCrest(dto.getHomeTeam().getCrest());
                            return teamRepository.save(newTeam);
                        });

                Team awayTeam = teamRepository.findById(dto.getAwayTeam().getId())
                        .orElseGet(() -> {
                            Team newTeam = new Team();
                            newTeam.setId(dto.getAwayTeam().getId());
                            newTeam.setName(dto.getAwayTeam().getName());
                            newTeam.setCrest(dto.getAwayTeam().getCrest());
                            return teamRepository.save(newTeam);
                        });

                Match match = new Match();
                match.setId(dto.getId());
                match.setUtcDate(dto.getUtcDate());
                match.setStatus(dto.getStatus());
                match.setHomeTeam(homeTeam);
                match.setAwayTeam(awayTeam);

                if (dto.getScore() != null && dto.getScore().getFullTime() != null) {
                    match.setHomeScore(dto.getScore().getFullTime().getHome());
                    match.setAwayScore(dto.getScore().getFullTime().getAway());
                }

                matchRepository.save(match);
            }
        }
    }

    public List<MatchDto> getALlMatches() {
        return matchRepository.findAll().stream().map(match -> {
            MatchDto dto = new MatchDto();
            dto.setId(match.getId());
            dto.setStatus(match.getStatus());
            dto.setUtcDate(match.getUtcDate());

            if (match.getHomeTeam() != null) {
                TeamDto homeTeamDto = new TeamDto();

                homeTeamDto.setId(match.getHomeTeam().getId());
                homeTeamDto.setName(match.getHomeTeam().getName());
                homeTeamDto.setCrest(match.getHomeTeam().getCrest());

                dto.setHomeTeam(homeTeamDto);
            }

            if (match.getAwayTeam() != null) {
                TeamDto awayTeamDto = new TeamDto();

                awayTeamDto.setId(match.getAwayTeam().getId());
                awayTeamDto.setName(match.getAwayTeam().getName());
                awayTeamDto.setCrest(match.getAwayTeam().getCrest());

                dto.setAwayTeam(awayTeamDto);
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
        }).toList();
    }

    public MatchDto getMatchById(Long id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + id));

        return mapToDto(match);
    }

    private MatchDto mapToDto(Match match) {
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


}