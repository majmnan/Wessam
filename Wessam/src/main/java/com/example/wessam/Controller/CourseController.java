package com.example.wessam.Controller;

import com.example.wessam.Api.ApiResponse;
import com.example.wessam.DTO.IN.CourseDTOIn;
import com.example.wessam.Model.Course;
import com.example.wessam.Model.User;
import com.example.wessam.Service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllCourses() {
        return ResponseEntity.status(200).body(courseService.getAllCourses());
    }
    @PostMapping("/add")
    public ResponseEntity<?> addCourse(@AuthenticationPrincipal User user, @RequestBody @Valid CourseDTOIn dto) {
        courseService.addCourse(user.getId(), dto);
        return ResponseEntity.status(200).body(new ApiResponse("course added successfully"));
    }

    @PutMapping("/update/{courseId}")
    public ResponseEntity<?> updateCourse(@AuthenticationPrincipal User user,@PathVariable Integer courseId,@RequestBody @Valid CourseDTOIn dtoIn) {
        courseService.updateCourse(user.getId(), courseId, dtoIn);
        return ResponseEntity.status(200).body(new ApiResponse("course updated successfully"));
    }

    @DeleteMapping("/delete/{courseId}")
    public ResponseEntity<?> deleteCourse(@AuthenticationPrincipal User user, @PathVariable Integer courseId) {
        courseService.deleteCourse(courseId, user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("course deleted successfully"));
    }


    @GetMapping("/get/top/{courseId}")
    public ResponseEntity<?> getTopCourse(@PathVariable Integer courseId) {
        return ResponseEntity.status(200).body(courseService.gettopCourses(courseId));
    }

    @GetMapping("/get/recommended/{traineeId}/{sportId}")
    public ResponseEntity<?> getRecommendedCourse(@PathVariable Integer traineeId,@PathVariable Integer sportId) {
        return ResponseEntity.status(200).body(courseService.getRecomendedCourses(traineeId, sportId));
    }

    @GetMapping("/get/dateRange/{sportId}/{startDate}/{endDate}")
    public ResponseEntity<?> getCoursesByDateRange(@PathVariable Integer sportId, @PathVariable String startDate, @PathVariable String endDate){
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return ResponseEntity.status(200).body(courseService.getCoursesByStartDateRange(sportId, start, end));
    }


    @GetMapping("/get/feedback/{courseId}")
    public ResponseEntity<?> analyzeFeedback(@PathVariable Integer courseId) {
        return ResponseEntity.status(200).body(courseService.courseFeedbackAi(courseId));

    }

    @GetMapping("/get/upcoming")
    public ResponseEntity<?> getUpcomingTournaments() {
        return ResponseEntity.status(200).body(courseService.getUpcomingCourses());
    }

}
