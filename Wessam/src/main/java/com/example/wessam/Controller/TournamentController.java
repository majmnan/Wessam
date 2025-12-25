package com.example.wessam.Controller;

import com.example.wessam.Service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tournament")
@RequiredArgsConstructor
public class TournamentController {
    private final TournamentService tournamentService;
}
