package com.example.padel.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLeagueRequest {
    private List<Long> pairs;
}
