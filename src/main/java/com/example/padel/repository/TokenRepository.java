package com.example.padel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.padel.model.Token;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findByToken(String token);

    @Modifying
    @Query("DELETE FROM Token token WHERE token.player.id = :playerId")
    void deleteByPlayerId(@Param("playerId") Long playerId);
}
