package com.example.padel.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.padel.model.Pair;
import com.example.padel.model.Player;
import com.example.padel.repository.PairRepository;
import com.example.padel.repository.PlayerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PairService {

    private final PairRepository pairRepository;
    private final PlayerRepository playerRepository;

    public Pair createPair(Long player1Id, Long player2Id) {
        Player player1 = playerRepository.findById(player1Id)
            .orElseThrow(() -> new RuntimeException("Player not found with id " + player1Id));
        Player player2 = playerRepository.findById(player2Id)
            .orElseThrow(() -> new RuntimeException("Player not found with id " + player2Id));

        Pair pair = new Pair();
        pair.setPlayer1(player1);
        pair.setPlayer2(player2);
        pair.setCreationDate(LocalDateTime.now());

        System.out.println("ovo je par: " + pair);

        return pairRepository.save(pair);
    }

    public List<Pair> getPairs() {
        return pairRepository.findAll();
    }
}
