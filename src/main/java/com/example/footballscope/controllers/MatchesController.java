package com.example.footballscope.controllers;

import com.example.footballscope.dto.MatchDto;
import com.example.footballscope.services.FootballDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchesController {

    private final FootballDataService footballDataService;

    public MatchesController(FootballDataService footballDataService) {
        this.footballDataService = footballDataService;
    }

    @GetMapping
    public List<MatchDto> getAllMatches() {
        return footballDataService.getAllMatches();
    }

    @GetMapping("/{id}")
    public MatchDto getMatchById(@PathVariable Long id) {
        return footballDataService.getMatchById(id);
    }

    @GetMapping("/league/{leagueId}")
    public List<MatchDto> getMatchesByLeague(@PathVariable Long leagueId) {
        return footballDataService.getMatchesByLeague(leagueId);
    }

    @GetMapping("/status/{status}")
    public List<MatchDto> getMatchesByStatus(@PathVariable String status) {
        return footballDataService.getMatchesByStatus(status);
    }

    @GetMapping("/league/{leagueId}/status/{status}")
    public List<MatchDto> getMatchesByLeagueAndStatus(
            @PathVariable Long leagueId,
            @PathVariable String status) {
        return footballDataService.getMatchesByLeagueAndStatus(leagueId, status);
    }
}