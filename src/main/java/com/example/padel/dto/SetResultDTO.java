package com.example.padel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SetResultDTO {
    private Integer setNumber;
    private Integer firstPairResult;
    private Integer secondPairResult;
}
