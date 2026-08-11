package com.example.footballscope.web;

import com.example.footballscope.dto.MatchDto;
import com.example.footballscope.services.FootballDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/matches")
public class MatchesPageController {

    private final FootballDataService footballDataService;

    public MatchesPageController(FootballDataService footballDataService) {
        this.footballDataService = footballDataService;
    }

    @GetMapping
    public String listMatches(Model model) {
        footballDataService.syncMatches();
        model.addAttribute("matches", footballDataService.getAllMatches());
        return "matches/list";
    }

    @GetMapping("/{id}")
    public String showMatchDetails(@PathVariable Long id, Model model) {
        MatchDto match = footballDataService.getMatchById(id);
        model.addAttribute("match", match);
        return "matches/details";
    }
}
