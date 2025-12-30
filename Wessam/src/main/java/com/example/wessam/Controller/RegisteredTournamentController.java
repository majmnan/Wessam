package com.example.wessam.Controller;

import com.example.wessam.Api.ApiResponse;
import com.example.wessam.Model.RegisteredTournament;
import com.example.wessam.Model.User;
import com.example.wessam.Service.RegisteredTournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/registeredTournament")
public class RegisteredTournamentController {

    private final RegisteredTournamentService registeredTournamentService;

    @GetMapping("/get")//todo: admin only auth
    public ResponseEntity<?> getAllRegisteredTournaments(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(registeredTournamentService.getAllRegisteredTournaments());
    }
    @PostMapping("/add/{tournamentId}")
    public ResponseEntity<?> addRegisteredTournament(@AuthenticationPrincipal User user, @PathVariable Integer tournamentId) {
        registeredTournamentService.addRegisteredTournament(user.getId(), tournamentId);
        return ResponseEntity.status(200).body(new ApiResponse("registration successful"));
    }
    @PutMapping("/update/{oldTournamentId}/{newTournamentId}")
    public ResponseEntity<?> updateRegisteredTournament(@AuthenticationPrincipal User user, @PathVariable Integer oldTournamentId, @PathVariable Integer newTournamentId) {
        registeredTournamentService.updateRegisteredTournament(user.getId(), oldTournamentId, newTournamentId);
        return ResponseEntity.status(200).body(new ApiResponse("registration updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRegisteredTournament(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        registeredTournamentService.deleteRegisteredTournament(user.getId(), id);
        return ResponseEntity.status(200).body(new ApiResponse("Registration canceled successfully"));
    }

    @GetMapping("/get/totalTrainee/{id}")
    public ResponseEntity<?> getTotalTrainees(@PathVariable Integer id){
        return ResponseEntity.status(200).body(registeredTournamentService.tournamentNumOfTrainees(id));
    }


}
