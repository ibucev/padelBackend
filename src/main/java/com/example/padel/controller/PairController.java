package com.example.padel.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.padel.dto.PairDTO;
import com.example.padel.service.PairService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pairs")
@Tag(name = "Pairs", description = "Pairs API")
public class PairController {

    private final PairService pairService;

    @PostMapping
    public PairDTO createPair(@RequestParam Long player1Id, @RequestParam Long player2Id) {
        return pairService.createPair(player1Id, player2Id);
    }

    @GetMapping
    public List<PairDTO> getAllPairs() {
        return pairService.getPairs();
    }
}
