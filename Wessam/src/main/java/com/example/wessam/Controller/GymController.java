package com.example.wessam.Controller;

import com.example.wessam.Api.ApiResponse;
import com.example.wessam.DTO.IN.GymDTOIn;
import com.example.wessam.Model.User;
import com.example.wessam.Service.GymService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gym")
public class GymController {

    private final GymService gymService;


    // Auth: ANY
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid GymDTOIn dto) {
        gymService.register(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Gym registered successfully"));
    }

    // Auth: GYM
    @PutMapping("/update")
    public ResponseEntity<?> updateGym(@AuthenticationPrincipal User user, @RequestBody @Valid GymDTOIn dto) {
        gymService.updateGym(user.getId(), dto);
        return ResponseEntity.status(HttpStatus.OK).body("Gym updated successfully");
    }

    // Auth: GYM
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteGym(@AuthenticationPrincipal User user) {
        gymService.deleteGym(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Gym deleted successfully");
    }

    @PostMapping("/report")
    public ResponseEntity<?> reportIncident(@AuthenticationPrincipal User user, @RequestBody String message) {
        gymService.reportIncident(user.getId(), message);
        return ResponseEntity.status(200).body(new ApiResponse("Incident reported successfully"));
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllGyms() {
        return ResponseEntity.status(200).body(gymService.getAllGyms());
    }

    // Auth: ADMIN
    @GetMapping("/get-inactive")
    public ResponseEntity<?> getInactiveGyms(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(gymService.getInactiveGyms());
    }

    // Auth: ANY
    @GetMapping("/get-active")
    public ResponseEntity<?> getActiveGyms() {
        return ResponseEntity.status(200).body(gymService.getActiveGyms());
    }

    // Auth: ADMIN
    @PutMapping("/activate/{gymId}")
    public ResponseEntity<?> activateGym(@PathVariable Integer gymId) {
        gymService.activateGym(gymId);
        return ResponseEntity.status(HttpStatus.OK).body("Gym activated successfully");
    }
    // Auth: ADMIN
    @PutMapping("/deactivate/{gymId}")
    public ResponseEntity<?> deactivateGym(@PathVariable Integer gymId) {
        gymService.deactivateGym(gymId);
        return ResponseEntity.status(HttpStatus.OK).body("Gym deactivate successfully");
    }

}
