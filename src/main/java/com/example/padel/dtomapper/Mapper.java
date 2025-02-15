package com.example.padel.dtomapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.padel.dto.LeagueDTO;
import com.example.padel.dto.MatchDTO;
import com.example.padel.dto.PairDTO;
import com.example.padel.dto.PlayerDTO;
import com.example.padel.dto.SetResultDTO;
import com.example.padel.model.League;
import com.example.padel.model.Match;
import com.example.padel.model.Pair;
import com.example.padel.model.Player;
import com.example.padel.model.SetResult;

@Component
public class Mapper {
        public LeagueDTO convertToLeagueDTO(League league) {
                return new LeagueDTO(
                                league.getId(),
                                league.getMatches().stream().map(this::convertToMatchDTO).collect(Collectors.toList()),
                                league.getPairs().stream().map(this::converToPairDTO).collect(Collectors.toList()));
        }

        public MatchDTO convertToMatchDTO(Match match) {
                return new MatchDTO(
                                match.getId(),
                                match.getMatchDate().toString(),
                                match.getFirstPair().getPlayer1().getFullName(),
                                match.getFirstPair().getPlayer2().getFullName(),
                                match.getSecondPair().getPlayer1().getFullName(),
                                match.getSecondPair().getPlayer2().getFullName(),
                                match.getSetResults().stream().map(this::convertToSetResultDTO)
                                                .collect(Collectors.toList()));
        }

        public SetResultDTO convertToSetResultDTO(SetResult setResult) {
                return new SetResultDTO(
                                setResult.getSetNumber(),
                                setResult.getFirstPairResult(),
                                setResult.getSecondPairResult());
        }

        public PairDTO converToPairDTO(Pair pair) {
                return new PairDTO(
                                pair.getId(),
                                new PlayerDTO(pair.getPlayer1().getId(), pair.getPlayer1().getFirstname(),
                                                pair.getPlayer1().getLastname(), pair.getPlayer1().getEmail(),
                                                pair.getPlayer1().getPosition()),
                                new PlayerDTO(pair.getPlayer2().getId(), pair.getPlayer2().getFirstname(),
                                                pair.getPlayer2().getLastname(), pair.getPlayer2().getEmail(),
                                                pair.getPlayer2().getPosition()),
                                pair.isActive(),
                                pair.getCreationDate());
        }

        public PlayerDTO convertToPlayerDTO(Player player) {
                return new PlayerDTO(
                                player.getId(),
                                player.getFirstname(),
                                player.getLastname(),
                                player.getEmail(),
                                player.getPosition());
        }

}
