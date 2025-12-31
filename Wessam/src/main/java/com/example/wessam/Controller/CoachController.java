package com.example.wessam.Controller;

import com.example.wessam.Api.ApiResponse;
import com.example.wessam.DTO.IN.CoachDTOIn;
import com.example.wessam.Model.User;
import com.example.wessam.Service.CoachService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coach")
public class CoachController {

    private final CoachService coachService;

    //Auth: any
    @GetMapping("/get")
    public ResponseEntity<?> getCoaches(){
        return ResponseEntity.status(200).body(coachService.getCoaches());
    }

    //Auth: any
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid CoachDTOIn coachDTOIn){
        coachService.register(coachDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Coach is registered successfully"));
    }

    //Auth: gym
    @PutMapping("/activate/{coachId}")
    public ResponseEntity<?> activateCoach(@AuthenticationPrincipal User user,@PathVariable Integer coachId){
        coachService.activateCoach(user.getId(),coachId);
        return ResponseEntity.status(200).body(new ApiResponse("coach activated successfully"));
    }

    //Auth: Coach
    @PutMapping("/update")
    public ResponseEntity<?> updateCoach(@AuthenticationPrincipal User user,@RequestBody @Valid CoachDTOIn coachDTOIn) {
        coachService.updateCoach(user.getId(), coachDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Coach is updated successfully"));
    }

    //Auth: Coach
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCoach(@AuthenticationPrincipal User user) {
        coachService.deleteCoach(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Coach is deleted successfully"));
    }

    @GetMapping("/available/{date}")
    public ResponseEntity<?> getAvailableCoaches(@AuthenticationPrincipal User user, @PathVariable LocalDate date) {
        return ResponseEntity.status(200).body(coachService.getAvailableCoaches(user.getId(), date));
    }
    @GetMapping("/dashboard/{coachId}")
    public ResponseEntity<?>getCoachDashboard(@PathVariable Integer coachId){
        return ResponseEntity.status(200).body(coachService.getCoachDashboard(coachId));

    }

    @GetMapping("/review-summary/{courseId}/{coachId}")
    public ResponseEntity<?> analyzeFeedback(@PathVariable Integer courseId,@PathVariable Integer coachId) {
        return ResponseEntity.status(200).body(coachService.coachFeedbackAiByCourse(coachId,coachId));

    }

    @GetMapping("/avg-rating/{coachId}")
    public ResponseEntity<?> getAverageRatings(@PathVariable Integer coachId){
        return ResponseEntity.status(200).body(coachService.getAvgCoachRatings(coachId));
    }

    @GetMapping("/total-trainee/{coachId}")
    public ResponseEntity<?> getTotalTrainees(@PathVariable Integer coachId){
        return ResponseEntity.status(200).body(coachService.getCoachTotalTrainees(coachId));
    }

    @GetMapping("/total-courses/{coachId}")
    public ResponseEntity<?> getTotalCourses(@PathVariable Integer coachId){
        return ResponseEntity.status(200).body(coachService.getCoachTotalCourses(coachId));
    }
}
