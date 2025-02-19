package com.example.padel.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.padel.dto.LeagueDTO;
import com.example.padel.dto.MatchDTO;
import com.example.padel.model.CreateLeagueRequest;
import com.example.padel.service.LeagueService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/leagues")
public class LeagueController {

    private final LeagueService leagueService;

    @PostMapping
    public LeagueDTO createLeague(@RequestBody CreateLeagueRequest leagueRequest ){
        return leagueService.createLeague(leagueRequest.getPairs());
    }

    @GetMapping("{leagueId}")
    public List<MatchDTO> getallMatchesInLeague(@PathVariable Long leagueId) {
        return leagueService.getallMatchesInLeague(leagueId);
    }
}
