package com.example.wessam.Repository;

import com.example.wessam.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
    Course findCourseById(Integer id);

    List<Course> findCoursesByEntryLevelAndStartDateAfter(String entryLevel, LocalDate startDateAfter);

    @Query("select COUNT(c) from Course c where c.coach.id=:coachId")
    Integer coachCount(@Param("coachId")Integer coachId);

    @Query("select c.coach.name from Course c where c.id=:courseId")
    String findcoachnameByCorse(@Param("courseId")Integer courseId);

    @Query("select c from Course c where c.coach.sport.id=:sportId AND c.entryLevel=:entryLevel AND c.startDate>:todayDate")
    List<Course>findCoursesBySports(@Param("sportId")Integer sportId, @Param("entryLevel")String entryLevel, @Param("todayDate")LocalDate TodayDate);

    @Query("select c from Course c where c.coach.sport.id = :sportId and c.startDate between :startDate and :endDate")
    List<Course> findCoursesByDate(@Param("sportId")Integer id,@Param("startDate")LocalDate startDate,@Param("endDate")LocalDate endDate);

    @Query("select c from Course c where c.startDate>:startDate")
    List<Course> findCoursesByStartDate(@Param("startDate")LocalDate startDate);

}
