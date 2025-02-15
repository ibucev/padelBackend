package com.example.padel.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.padel.dto.LeagueDTO;
import com.example.padel.dtomapper.Mapper;
import com.example.padel.model.League;
import com.example.padel.model.Match;
import com.example.padel.model.Pair;
import com.example.padel.model.SetResult;
import com.example.padel.repository.LeagueRepository;
import com.example.padel.repository.MatchRepository;
import com.example.padel.repository.PairRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LeagueService {

    private final MatchRepository matchRepository;
    private final LeagueRepository leagueRepository;
    private final PairRepository pairRepository;
    private final Mapper leagueDtoMapper;
    private final Logger logger = LoggerFactory.getLogger(LeagueService.class);

    @Transactional
    public LeagueDTO createLeague(List<Long> pairs) {
        League league = new League();
        List<Pair> listOfPairs = new ArrayList<>();

        for (Long pairElement : pairs) {
            Pair pair = pairRepository.findById(pairElement)
                    .orElseThrow(() -> new RuntimeException("Pair no found with id " + pairElement));
            listOfPairs.add(pair);
        }

        league.setPairs(listOfPairs);
        league.setLeagueCreated(LocalDateTime.now());
        league.setLeagueDuration(30);

        List<Match> matches = generateAllMatchesInLeague(listOfPairs, league);
        league.setMatches(matches);
        leagueRepository.save(league);
        matchRepository.saveAll(matches);

        return leagueDtoMapper.convertToLeagueDTO(league);
    }

    public List<Match> generateAllMatchesInLeague(List<Pair> pairsInLeague, League league) {
        List<Match> allMatchesInLeague = new ArrayList<>();

        for (int i = 0; i < pairsInLeague.size(); i++) {
            for (int j = i + 1; j < pairsInLeague.size(); j++) {
                Match match = new Match();
                match.setFirstPair(pairsInLeague.get(i));
                match.setSecondPair(pairsInLeague.get(j));
                match.setSetResults(new ArrayList<SetResult>());
                match.setMatchDate(LocalDateTime.now());
                match.setLeague(league);
                logger.debug(match.getFirstPair().getPlayer1().getFullName() + " - "
                        + match.getSecondPair().getPlayer1().getFullName());
                allMatchesInLeague.add(match);
            }
        }
        return allMatchesInLeague;
    }
}
