package com.example.wessam.Controller;

import com.example.wessam.Api.ApiResponse;
import com.example.wessam.DTO.IN.CoachDTOIn;
import com.example.wessam.DTO.IN.TraineeDTOIn;
import com.example.wessam.Model.Coach;
import com.example.wessam.Model.User;
import com.example.wessam.Service.CoachService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coach")
public class CoachController {

    private final CoachService coachService;


    @GetMapping("/get")
    public ResponseEntity<?> getCoachs(){
        return ResponseEntity.status(200).body(coachService.getCoaches());
    }


    @PostMapping("/add")
    public ResponseEntity<?> addCoach(@RequestBody @Valid CoachDTOIn coachDTOIn){
        coachService.addCoach(coachDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Coach is addeed successfully"));
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateCoach(@AuthenticationPrincipal User user,@RequestBody @Valid CoachDTOIn coachDTOIn) {
        coachService.updateCoach(user.getId(), coachDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Coach is updated successfully"));
    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCoach(@AuthenticationPrincipal User user,@PathVariable Integer id) {
        coachService.deleteCoach(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Coach is deleted successfully"));
    }


}
