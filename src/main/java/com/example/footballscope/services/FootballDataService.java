package com.example.footballscope.services;

import com.example.footballscope.dto.MatchDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class FootballDataService {
    @Value("${football.api.token}")
    private String apiToken;

    public MatchDto getMatchesFromApi(){
        RestClient restClient = RestClient.create();

        return restClient.get()
                .uri("https://api.football-data.org/v4/competitions/PL/matches")
                .header("X-Auth-Token",apiToken)
                .retrieve()
                .body(MatchDto.class);
    }

}
