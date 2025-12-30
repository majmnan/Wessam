package com.example.wessam.Controller;

import com.example.wessam.Api.ApiResponse;
import com.example.wessam.DTO.IN.CourseDTOIn;
import com.example.wessam.Model.User;
import com.example.wessam.Service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/next-level-courses")
    public ResponseEntity<?> nextLevelCourses(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(courseService.nextLevelCourses(user.getId()));
    }





}
