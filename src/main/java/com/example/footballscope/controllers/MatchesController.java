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
        return footballDataService.getALlMatches();
    }

    @GetMapping("/{id}")
    public MatchDto getMatchById(@PathVariable Long id){
        return footballDataService.getMatchById(id);
    }



}
