package com.example.wessam.Repository;

import com.example.wessam.Model.CourseReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseReviewRepository extends JpaRepository<CourseReview, Integer> {
    CourseReview findCourseReviewById(Integer id);
}
