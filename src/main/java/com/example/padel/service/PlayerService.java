package com.example.padel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private Integer countPairNumber = 0;

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Player with this id: " + id + " does not exist"));
    }

    @Transactional
    public void deletePlayerById(Long id) {
        Player player = getPlayerById(id);

        countPairNumber = pairRepository.countPairsByPlayerId(id);

        if (countPairNumber > 0) {
            throw new PlayerDeleteException(String.format("Cannot delete a player %s, it has an active pair", player.getFullName()));
        }

        tokenRepository.deleteByPlayerId(id);
        playerRepository.delete(player);
    }

    public Player editPlayer(Long id, Player playerInfo) {
        Player player = getPlayerById(id);
        player.setFirstname(playerInfo.getFirstname());
        player.setLastname(playerInfo.getLastname());
        player.setEmail(playerInfo.getEmail());
        player.setPosition(playerInfo.getPosition());

        return playerRepository.save(player);
    }

}
