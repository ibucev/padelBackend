package com.example.padel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.padel.model.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query(value = "SELECT " +
            "m.id AS match_id, " +
            "m.match_date, " + 
            "p1.lastname AS first_pair_player1, " +
            "p2.lastname AS first_pair_player2, " +
            "p3.lastname AS second_pair_player1, " +
            "p4.lastname AS second_pair_player2, " +
            "s.set_number AS setNumber, " +
            "s.first_pair_result AS firstPairResult, " +
            "s.second_pair_result AS secondPairResult " +
            "FROM match m " +
            "JOIN pairs first_pair ON m.firstpair_id = first_pair.id " +
            "JOIN pairs second_pair ON m.secondpair_id = second_pair.id " +
            "JOIN players p1 ON first_pair.player1_id = p1.id " +
            "JOIN players p2 ON first_pair.player2_id = p2.id " +
            "JOIN players p3 ON second_pair.player1_id = p3.id " +
            "JOIN players p4 ON second_pair.player2_id = p4.id " +
            "JOIN set_result s ON m.id = s.match_id " +
            "WHERE (:playerId IS NULL OR p1.id = :playerId OR p2.id = :playerId OR p3.id = :playerId OR p4.id = :playerId)", 
            nativeQuery = true)
    List<Object[]> getMatchesWithSets(@Param("playerId") Long playerId);   
}
