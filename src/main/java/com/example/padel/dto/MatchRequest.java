package com.example.padel.dto;

import java.util.List;

import com.example.padel.model.SetResult;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchRequest {
    private Long firstPair;
    private Long secondPair;
    private List<SetResult> setResults;
}
