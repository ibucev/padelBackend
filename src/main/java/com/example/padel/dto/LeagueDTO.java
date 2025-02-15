package com.example.padel.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LeagueDTO {
    private Integer id;
    private List<MatchDTO> leagueMatches;
    private List<PairDTO> leaguePairs;
}
