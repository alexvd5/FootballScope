package com.example.footballscope.controllers;

import com.example.footballscope.services.FootballDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SyncController {

    private final FootballDataService footballDataService;

    public SyncController(FootballDataService footballDataService) {
        this.footballDataService = footballDataService;
    }

    @GetMapping("/sync")
    public ResponseEntity<String> syncData() {
        footballDataService.syncMatches();
        return ResponseEntity.ok("Synchronized ! Check your database !");
    }
}