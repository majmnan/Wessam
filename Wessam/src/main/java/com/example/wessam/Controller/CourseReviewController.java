package com.example.wessam.Controller;

import com.example.wessam.DTO.IN.CourseReviewDTOIn;
import com.example.wessam.Service.CourseReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.JsonNode;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course-review")
@RequiredArgsConstructor
public class CourseReviewController {

    private final CourseReviewService courseReviewService;


    @GetMapping("/get/{courseId}")
    public ResponseEntity<?> getCourseReviews(@PathVariable Integer courseId) {
        return ResponseEntity.ok(courseReviewService.getCourseReviews(courseId));
    }


    @PostMapping("/add")
    public ResponseEntity<?> reviewACourse(
            @AuthenticationPrincipal Integer traineeId,
            @RequestBody CourseReviewDTOIn dto
    ) {
        courseReviewService.reviewACourse(traineeId, dto);
        return ResponseEntity.ok("Course reviewed successfully");
    }


    @PutMapping("/update/{reviewId}")
    public ResponseEntity<?> updateReview(
            @AuthenticationPrincipal Integer traineeId,
            @PathVariable Integer reviewId,
            @RequestBody CourseReviewDTOIn dto
    ) {
        courseReviewService.updateReview(traineeId, reviewId, dto);
        return ResponseEntity.ok("Review updated successfully");
    }


    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<?> deleteReview(
            @AuthenticationPrincipal Integer traineeId,
            @PathVariable Integer reviewId
    ) {
        courseReviewService.deleteReview(traineeId, reviewId);
        return ResponseEntity.ok("Review deleted successfully");
    }

    @GetMapping("/summary/{courseId}")
    public ResponseEntity<JsonNode> courseReviewSummary(@PathVariable Integer courseId) {
        return ResponseEntity.ok(courseReviewService.CourseReviewSummary(courseId));
    }
}
