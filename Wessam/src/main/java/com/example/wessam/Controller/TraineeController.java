package com.example.wessam.Controller;

import com.example.wessam.Api.ApiResponse;
import com.example.wessam.DTO.IN.TraineeDTOIn;
import com.example.wessam.Model.User;
import com.example.wessam.Service.TraineeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trainee")
@RequiredArgsConstructor
public class TraineeController {
    private final TraineeService traineeService;

    //Auth: Admin
    @GetMapping("/get")
    public ResponseEntity<?> getAllTrainees(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(traineeService.getAllTrainees());
    }

    //Auth: any
    @PostMapping("/register")
    public ResponseEntity<?> registerTrainee(@RequestBody @Valid TraineeDTOIn traineeDTOIn) {
        traineeService.register(traineeDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Trainee registered successfully"));
    }

    //Auth: Trainee
    @PutMapping("/update")
    public ResponseEntity<?> updateTrainee(@AuthenticationPrincipal User user, @RequestBody @Valid TraineeDTOIn traineeDTOIn) {
        traineeService.updateTrainee(user.getId(), traineeDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Trainee profile updated successfully"));
    }

    //Auth: Trainee
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTrainee(@AuthenticationPrincipal User user) {
        traineeService.deleteTrainee(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Trainee account deleted successfully"));
    }

}
