package com.example.wessam.Repository;

import com.example.wessam.Model.Course;
import com.example.wessam.Model.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Integer> {
    CourseRegistration findCourseRegistrationById(Integer id);
    List<CourseRegistration> findCourseRegistrationByCourseId(Integer courseId);

    List<CourseRegistration> findAllByStatus(String status);

    @Query("select COUNT(DISTINCT cr.trainee.id)" +
            " from CourseRegistration cr " +
            "where cr.course.coach.id=:coachId")
    Integer TraineeCount(@Param("coachId")Integer coachId);

    @Query("select COUNT(DISTINCT cr.trainee.id)" +
            " from CourseRegistration cr " +
            "where cr.course.id=:courseId")
    Integer TraineeCourseCount(@Param("courseId")Integer courseId);

    @Query("select cr.course from CourseRegistration cr where cr.trainee.id=:traineeId")
    List<Course> findRegestedCourses(@Param("traineeId")Integer traineeId);


    @Query("select count(distinct cr.trainee.id) from CourseRegistration cr where cr.course.coach.sport.id=:sportId")
    Integer countTraineesBySport(@Param("sportId") Integer sportId);

    @Query("select avg(r.rate)from CourseRegistration cr join cr.course c join c.coach coach join cr.courseReviews r where coach.sport.id = :sportId")
    Double avgRatingBySport(@Param("sportId") Integer sportId);



}
