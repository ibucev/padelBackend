package com.example.padel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.padel.model.Pair;

@Repository
public interface PairRepository extends JpaRepository<Pair, Long> {

    @Query("SELECT COUNT(p) FROM Pair p WHERE p.player1.id = :playerId OR p.player2.id = :playerId")
    Integer countPairsByPlayerId(@Param("playerId") Long id);
}
