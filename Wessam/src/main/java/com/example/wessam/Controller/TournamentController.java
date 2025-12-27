package com.example.wessam.Controller;

import com.example.wessam.Api.ApiResponse;
import com.example.wessam.Model.Tournament;
import com.example.wessam.Model.User;
import com.example.wessam.Service.TournamentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tournament")
@RequiredArgsConstructor
public class TournamentController {
    private final TournamentService tournamentService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllTournaments() {
        return ResponseEntity.status(200).body(tournamentService.getAllTournaments());
    }

    @PostMapping("/add/{sportId}")
    public ResponseEntity<?> addTournament(@AuthenticationPrincipal User user, @RequestBody @Valid Tournament tournament, @PathVariable Integer sportId) {
        tournamentService.addTournament(user.getId(), tournament, sportId);
        return ResponseEntity.status(200).body(new ApiResponse("Tournament added successfully"));
    }

    @PutMapping("/update/{tournamentId}/{sportId}")
    public ResponseEntity<?> updateTournament(@AuthenticationPrincipal User user,@PathVariable Integer tournamentId,@PathVariable Integer sportId,@RequestBody @Valid Tournament tournament) {
        tournamentService.updateTournament(user.getId(), tournamentId, sportId, tournament);
        return ResponseEntity.status(200).body(new ApiResponse("Tournament updated successfully"));
    }

    @DeleteMapping("/delete/{tournamentId}")
    public ResponseEntity<?> deleteTournament(@AuthenticationPrincipal User user, @PathVariable Integer tournamentId) {
        tournamentService.deleteTournament(user.getId(), tournamentId);
        return ResponseEntity.status(200).body(new ApiResponse("Tournament deleted successfully"));
    }

}
