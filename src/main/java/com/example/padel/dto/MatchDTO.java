package com.example.padel.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MatchDTO {
    private Integer matchId;
    private String matchDate;
    private String firstPairPlayer1;
    private String firstPairPlayer2;
    private String secondPairPlayer1;
    private String secondPairPlayer2;
    private List<SetResultDTO> setResults;
}
