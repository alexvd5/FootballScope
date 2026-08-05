package com.example.footballscope.repositories;

import com.example.footballscope.models.Standing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StandingRepository extends JpaRepository<Standing,Long> {
    List<Standing> findByLeagueInOrderByPositionAsc(Long leagueId);

    void deleteByLeagueId(Long leagueId);
}
