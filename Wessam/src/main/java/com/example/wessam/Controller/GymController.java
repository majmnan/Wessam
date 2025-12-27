package com.example.wessam.Controller;

import com.example.wessam.Api.ApiResponse;
import com.example.wessam.DTO.IN.CoachDTOIn;
import com.example.wessam.DTO.IN.GymDTOIn;
import com.example.wessam.Model.User;
import com.example.wessam.Service.GymService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gym")
public class GymController {



    private final GymService gymService;

    @GetMapping("/get")
    public ResponseEntity<?> getGyms() {
        return ResponseEntity.ok(gymService.getGyms());
    }


    @PostMapping("/add")
    public ResponseEntity<?> addGym(@RequestBody @Valid GymDTOIn gymDTOIn) {
        gymService.addGym(gymDTOIn);
        return ResponseEntity.ok(new ApiResponse("Gym is added successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateGym(@AuthenticationPrincipal User user, @RequestBody @Valid GymDTOIn gymDTOIn) {
        gymService.updateGym(user.getId(), gymDTOIn);
        return ResponseEntity.ok(new ApiResponse("Gym is updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGym(@PathVariable Integer id) {
        gymService.deleteGym(id);
        return ResponseEntity.ok(new ApiResponse("Gym is deleted successfully"));
    }
}
