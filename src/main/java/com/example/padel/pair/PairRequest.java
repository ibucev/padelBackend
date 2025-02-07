package com.example.padel.pair;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PairRequest {
        private Long player1Id;
        private Long player2Id;

}
