package com.example.wessam.Repository;

import com.example.wessam.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
    Course findCourseById(Integer id);

    List<Course> findCoursesByEntryLevelAndStartDateAfter(String entryLevel, LocalDate startDateAfter);
}
