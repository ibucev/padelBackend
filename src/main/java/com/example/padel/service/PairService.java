package com.example.padel.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.padel.dto.PairDTO;
import com.example.padel.dtomapper.Mapper;
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
    private final Mapper pairDtoMapper;
    private final Logger logger = LoggerFactory.getLogger(PairService.class);

    public PairDTO createPair(Long player1Id, Long player2Id) {
        Player player1 = playerRepository.findById(player1Id)
            .orElseThrow(() -> new RuntimeException("Player not found with id " + player1Id));
        Player player2 = playerRepository.findById(player2Id)
            .orElseThrow(() -> new RuntimeException("Player not found with id " + player2Id));

        Pair pair = new Pair();
        pair.setPlayer1(player1);
        pair.setPlayer2(player2);
        pair.setCreationDate(LocalDateTime.now());

        logger.info("Pair created of players ids: {} and {}", player1Id, player2Id);

        pairRepository.save(pair);
        return pairDtoMapper.converToPairDTO(pair);
    }

    public List<PairDTO> getPairs() {
        return pairRepository.findAll()
            .stream()
            .map(pairDtoMapper::converToPairDTO)
            .collect(Collectors.toList());
    }
}
