package com.example.footballscope.services;

import com.example.footballscope.dto.*;
import com.example.footballscope.mappers.MatchMapper;
import com.example.footballscope.models.League;
import com.example.footballscope.models.Match;
import com.example.footballscope.models.Team;
import com.example.footballscope.repositories.LeagueRepository;
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
    private final LeagueRepository leagueRepository;
    private final MatchMapper matchMapper;

    public FootballDataService(MatchRepository matchRepository, TeamRepository teamRepository, LeagueRepository leagueRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
        this.leagueRepository = leagueRepository;
        this.matchMapper = new MatchMapper();
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

                if (dto.getCompetition() != null) {
                    Long compId = dto.getCompetition().getId();
                    League league = leagueRepository.findById(compId)
                            .orElseGet(() -> {
                                League newLeague = new League();
                                newLeague.setId(dto.getCompetition().getId());
                                newLeague.setName(dto.getCompetition().getName());
                                newLeague.setCode(dto.getCompetition().getCode());
                                return leagueRepository.save(newLeague);
                            });
                    match.setLeague(league);
                }

                if (dto.getScore() != null && dto.getScore().getFullTime() != null) {
                    match.setHomeScore(dto.getScore().getFullTime().getHome());
                    match.setAwayScore(dto.getScore().getFullTime().getAway());
                }

                matchRepository.save(match);
            }
        }
    }

    public List<MatchDto> getAllMatches() {
        return matchMapper.toDtoList(matchRepository.findAll());
    }

    public List<MatchDto> getMatchesByLeague(Long leagueId) {
        return matchMapper.toDtoList(matchRepository.findByLeagueId(leagueId));
    }

    public List<MatchDto> getMatchesByStatus(String status) {
        return matchMapper.toDtoList(matchRepository.findByStatus(status));
    }

    public List<MatchDto> getMatchesByLeagueAndStatus(Long leagueId, String status) {
        return matchMapper.toDtoList(matchRepository.findByLeagueIdAndStatus(leagueId, status));
    }

    public MatchDto getMatchById(Long id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + id));

        return matchMapper.toDto(match);
    }

}