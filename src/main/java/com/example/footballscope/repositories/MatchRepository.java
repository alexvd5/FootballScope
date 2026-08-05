package com.example.footballscope.repositories;

import com.example.footballscope.models.League;
import com.example.footballscope.models.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByLeagueId(Long leagueId);

    List<Match> findByStatus(String status);

    List<Match> findByLeagueIdAndStatus(Long leagueId, String status);
}
