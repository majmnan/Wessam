package com.example.wessam.Repository;

import com.example.wessam.Model.CourseReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseReviewRepository extends JpaRepository<CourseReview, Integer> {
    CourseReview findCourseReviewById(Integer id);
    List<CourseReview> findCourseReviewsByRegistration_Course_Id(Integer courseId);

    @Query("select AVG(co.rate)" +
            " from CourseReview co " +
            "where co.registration.course.id=:courseId")
    Double aveRatings(@Param("courseId")Integer courseId);

    @Query("select rev from CourseReview rev where rev.registration.course.id=:courseId ")
    List<CourseReview> findAllReviewByCourse(@Param("courseId")Integer courseId);
}
