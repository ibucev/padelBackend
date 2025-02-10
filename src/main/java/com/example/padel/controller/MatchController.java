package com.example.padel.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.padel.dto.MatchDTO;
import com.example.padel.model.Match;
import com.example.padel.service.MatchService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;

    @GetMapping
    public List<MatchDTO> getAllMatches(){
        return matchService.getAllMatches();
    }

    @GetMapping("/{playerId}")
    public List<MatchDTO> getMatchesForPlayer(@PathVariable Long playerId) {
        return matchService.getAllMatchesForPlayer(playerId);
    }

    @PostMapping
    public Match createMatch(@RequestBody MatchRequest matchRequest) {
        return matchService.createMatch(matchRequest.getFirstPair(), 
            matchRequest.getSecondPair(),
            matchRequest.getSetResults());
    }   

}
