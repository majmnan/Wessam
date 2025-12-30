package com.example.wessam.Controller;

import com.example.wessam.Api.ApiResponse;
import com.example.wessam.DTO.IN.CardDTOIn;
import com.example.wessam.DTO.OUT.PaymentResponseDTO;
import com.example.wessam.Model.User;
import com.example.wessam.Service.CourseRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course-registration")
@RequiredArgsConstructor
public class CourseRegistrationController {

    private final CourseRegistrationService courseRegistrationService;

    // Auth: Coach
    @GetMapping("/get-by-course/{courseId}")
    public ResponseEntity<?> getRegistrationsOfCourse(@AuthenticationPrincipal User user, @PathVariable Integer courseId) {
        return ResponseEntity.status(HttpStatus.OK).body(courseRegistrationService.getRegistrationsOfACourse(user.getId(), courseId));
    }

    // Auth: Trainee
    @PostMapping("/register/{courseId}")
    public ResponseEntity<?> registerInCourse(@AuthenticationPrincipal User user, @PathVariable Integer courseId, @RequestBody@Valid CardDTOIn card) {
        return courseRegistrationService.registerInCourse(user.getId(), courseId, card);
    }

    // Auth: Trainee
    @DeleteMapping("/delete/{registrationId}")
    public ResponseEntity<?> deleteRegistration(@AuthenticationPrincipal User user, @PathVariable Integer registrationId) {
        courseRegistrationService.deleteRegistration(user.getId(), registrationId);
        return ResponseEntity.status(HttpStatus.OK).body("Registration deleted successfully");
    }

    //Auth: Trainee
    @PutMapping("/pay-pending/{registrationId}")
    public ResponseEntity<?> payPendingRegistration(@AuthenticationPrincipal User user, @PathVariable Integer registrationId, @RequestBody CardDTOIn card){
        return courseRegistrationService.payPendingRegistration(user.getId(),registrationId,card);
    }

    @PutMapping("/complete-payment")
    public ResponseEntity<?> checkPayment(@RequestParam("id") String id){
        courseRegistrationService.checkPayment(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("paid successfully and class activated"));
    }
}

