package com.example.wessam.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.wessam.DTO.IN.TraineeFeedbackDTOIn;
import com.example.wessam.DTO.OUT.TraineeFeedbackDTOOut;
import com.example.wessam.Service.TraineeFeedbackService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trainee-feedback")
@RequiredArgsConstructor
public class TraineeFeedbackController {

    private final TraineeFeedbackService traineeFeedbackService;


    @GetMapping("/get")
    public List<TraineeFeedbackDTOOut> getMyFeedbacks(
            @AuthenticationPrincipal Integer traineeId
    ) {
        return traineeFeedbackService.getFeedbacksByTraineeId(traineeId);
    }

    @PostMapping("/add")
    public void feedbackATrainee(
            @AuthenticationPrincipal Integer coachId,
            @RequestBody TraineeFeedbackDTOIn dto
    ) {
        traineeFeedbackService.feedbackATrainee(coachId, dto);
    }
}

