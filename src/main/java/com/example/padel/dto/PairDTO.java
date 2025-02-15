package com.example.padel.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PairDTO {
    private Integer id;
    private PlayerDTO player1;
    private PlayerDTO player2;
    private boolean active;
    private LocalDateTime dateCreation;
}
