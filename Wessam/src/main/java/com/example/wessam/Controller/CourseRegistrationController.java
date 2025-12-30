package com.example.wessam.Controller;

import com.example.wessam.Model.User;
import com.example.wessam.Service.CourseRegistrationService;
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
    public ResponseEntity<?> registerInCourse(@AuthenticationPrincipal User user, @PathVariable Integer courseId) {
        courseRegistrationService.registerInCourse(user.getId(), courseId);
        return ResponseEntity.status(HttpStatus.OK).body("Registered successfully");
    }

    // Auth: Trainee
    @DeleteMapping("/delete/{registrationId}")
    public ResponseEntity<?> deleteRegistration(@AuthenticationPrincipal User user, @PathVariable Integer registrationId) {
        courseRegistrationService.deleteRegistration(user.getId(), registrationId);
        return ResponseEntity.status(HttpStatus.OK).body("Registration deleted successfully");
    }

    @GetMapping("/get/registerd/{traineeId}")
    public ResponseEntity<?> getRegesteredCourses(@PathVariable Integer traineeId) {
        return ResponseEntity.status(200).body(courseRegistrationService.getCoursesRegestered(traineeId));
    }

    @GetMapping("/get/totalTrainee/{id}")
    public ResponseEntity<?> getTotalTrainees(@PathVariable Integer id){
        return ResponseEntity.status(200).body(courseRegistrationService.courseNumOfTrainees(id));
    }


}

