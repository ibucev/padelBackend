package com.example.padel.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.padel.dto.PlayerDTO;
import com.example.padel.dtomapper.Mapper;
import com.example.padel.exception.PlayerDeleteException;
import com.example.padel.exception.ResourceNotFoundException;
import com.example.padel.model.Player;
import com.example.padel.repository.PairRepository;
import com.example.padel.repository.PlayerRepository;
import com.example.padel.repository.TokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TokenRepository tokenRepository;
    private final PairRepository pairRepository;
    private final Mapper playerDtoMapper;
    private Integer activePairNumber = 0;
    private final Logger logger = LoggerFactory.getLogger(PairService.class);

    public List<PlayerDTO> getAllPlayers() {
        return playerRepository.findAll()
                .stream()
                .map(playerDtoMapper::convertToPlayerDTO)
                .collect(Collectors.toList());
    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Player with id: %d does not exist", id)));
    }

    @Transactional
    public void deletePlayerById(Long id) {
        Player player = getPlayerById(id);

        activePairNumber = pairRepository.countPairsByPlayerId(id);

        if (activePairNumber > 0) {
            throw new PlayerDeleteException(
                    String.format("Cannot delete a player %s, it has an active pair", player.getFullName()));
        }

        try {
            tokenRepository.deleteByPlayerId(id);
            playerRepository.delete(player);
            logger.info("Player with id {} deleted!", id);
        } catch (Exception e) {
            logger.error("Failed to delete a player with id {}", id);
            throw new PlayerDeleteException(
                    String.format("Cannot delete a player with id %d, error message: %s", id, e.getMessage()));
        }
    }

    public PlayerDTO editPlayer(Long id, Player playerInfo) {
        Player player = getPlayerById(id);
        player.setFirstname(playerInfo.getFirstname());
        player.setLastname(playerInfo.getLastname());
        player.setEmail(playerInfo.getEmail());
        player.setPosition(playerInfo.getPosition());

        playerRepository.save(player);
        return playerDtoMapper.convertToPlayerDTO(player);
    }
}
