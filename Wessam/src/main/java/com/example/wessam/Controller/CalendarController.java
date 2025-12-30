package com.example.wessam.Controller;
import com.example.wessam.Model.Course;
import com.example.wessam.Repository.CourseRepository;
import com.example.wessam.Service.CalendarEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/calendar")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarEventService calendarEventService;
    private final CourseRepository courseRepository;

    @PostMapping("/reminder/{courseId}")
    public ResponseEntity<?> createCourseReminder(@PathVariable Integer courseId) {
        Course course = courseRepository.findCourseById(courseId);
        if (course == null) {
            return ResponseEntity.status(404).body("Course not found");
        }

        try {
            Date reminderDate = Date.from(
                    course.getEndDate().minusDays(3)
                            .atStartOfDay(java.time.ZoneId.systemDefault())
                            .toInstant()
            );

            calendarEventService.createCourseReminder(course.getName(), reminderDate);
            return ResponseEntity.ok("Reminder added to Google Calendar");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
