package com.example.wessam.Controller;

import com.example.wessam.Api.ApiResponse;
import com.example.wessam.DTO.IN.CardDTOIn;
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

    //Auth: Coach
    @PutMapping("/complete/{registrationId}")
    public ResponseEntity<?> markAsCompleted(@AuthenticationPrincipal User user, @PathVariable Integer registrationId) {
        courseRegistrationService.markAsCompleted(user.getId(), registrationId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Course marked as completed"));
    }

    //Auth: Coach
    @PutMapping("/drop/{registrationId}")
    public ResponseEntity<?> markAsDropped(@AuthenticationPrincipal User user, @PathVariable Integer registrationId) {
        courseRegistrationService.markAsDropped(user.getId(), registrationId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Course marked as DROPPED"));
    }

    //Auth: any
    @GetMapping("/completed")
    public ResponseEntity<?> getCompletedRegistrations() {
        return ResponseEntity.status(HttpStatus.OK).body(courseRegistrationService.getCompletedRegistrations());
    }

    //Auth: Admin
    @GetMapping("/dropped")
    public ResponseEntity<?> getDroppedRegistrations() {
        return ResponseEntity.status(HttpStatus.OK).body(courseRegistrationService.getDroppedRegistrations());
    }

    //Auth: Trainee
    @PutMapping("/pay-pending/{registrationId}")
    public ResponseEntity<?> payPendingRegistration(@AuthenticationPrincipal User user, @PathVariable Integer registrationId, @RequestBody CardDTOIn card){
        return courseRegistrationService.payPendingRegistration(user.getId(),registrationId,card);
    }

    @GetMapping("/complete-payment/{registrationId}")
    public ResponseEntity<?> checkPayment(@PathVariable Integer registrationId, @RequestParam("id") String id){
        courseRegistrationService.checkPayment(registrationId, id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("paid successfully and class activated"));
    }

    //Auth: trainee
    @PostMapping("/certificates/{courseId}")
    public ResponseEntity<?> generateCertificate(@AuthenticationPrincipal User user,@PathVariable Integer courseId) {
        courseRegistrationService.generateCertificate(user.getId(), courseId);
        return ResponseEntity.status(200).body(new ApiResponse("Certificate was generated and sent successfully"));
    }

    //Auth: Trainee
    @GetMapping("/get/registerd/{traineeId}")
    public ResponseEntity<?> getRegesteredCourses(@PathVariable Integer traineeId) {
        return ResponseEntity.status(200).body(courseRegistrationService.getCoursesRegestered(traineeId));
    }

    //Auth: any
    @GetMapping("/get/totalTrainee/{id}")
    public ResponseEntity<?> getTotalTrainees(@PathVariable Integer id){
        return ResponseEntity.status(200).body(courseRegistrationService.courseNumOfTrainees(id));
    }

}

