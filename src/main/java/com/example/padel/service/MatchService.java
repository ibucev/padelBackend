package com.example.padel.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.padel.dto.MatchDTO;
import com.example.padel.dto.SetResultDTO;
import com.example.padel.model.Match;
import com.example.padel.model.Pair;
import com.example.padel.model.SetResult;
import com.example.padel.repository.MatchRepository;
import com.example.padel.repository.PairRepository;
import com.example.padel.repository.SetResultRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final PairRepository pairRepository;
    private final SetResultRepository setResultRepository;

    public List<MatchDTO> getAllMatches() {
        List<Object[]> setResults = matchRepository.getMatchesWithSets();
        Map<Integer, MatchDTO> matchMap = new HashMap<>();

        for (Object[] row : setResults) {
            Integer matchId = (Integer) row[0];
            MatchDTO matchDTO = matchMap.get(matchId);

            if (matchDTO == null) {
                matchDTO = new MatchDTO(
                    matchId,
                    formatMatchDate((Date) row[1]),
                    String.valueOf(row[2]),
                    String.valueOf(row[3]),
                    String.valueOf(row[4]),
                    String.valueOf(row[5]),
                    new ArrayList<>() 
                );
                matchMap.put(matchId, matchDTO);
            }
            SetResultDTO setResultDTO = new SetResultDTO(
                (Integer) row[6],
                (Integer) row[7],
                (Integer) row[8]
            );
            matchDTO.getSetResults().add(setResultDTO);
        }
        return new ArrayList<MatchDTO>(matchMap.values());
    }

    public String formatMatchDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy HH:mm");
        return formatter.format(date);
    }

    public Match createMatch(Long firstPair, Long secondPair, List<SetResult> result) {
        Match match = new Match();

        Pair pairOne = pairRepository.findById(firstPair)
            .orElseThrow(() -> new RuntimeException("Pair not found with id " + firstPair));
        Pair pairTwo = pairRepository.findById(secondPair)
            .orElseThrow(() -> new RuntimeException("Pair not found with id " + secondPair));

        match.setFirstPair(pairOne);
        match.setSecondPair(pairTwo);
        match.setMatchDate(LocalDateTime.now());
        match = matchRepository.save(match);

        for (SetResult setResult : result) {
            setResult.setMatch(match);
            setResultRepository.save(setResult);
        }
        return match;
    }
}
