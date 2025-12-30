package com.example.wessam.Repository;

import com.example.wessam.Model.TraineeFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TraineeFeedbackRepository extends JpaRepository<TraineeFeedback,Integer> {
    TraineeFeedback findTraineeFeedbackById(Integer id);
    List<TraineeFeedback> findTraineeFeedbacksByCourseRegistration_Trainee_Id(Integer courseId);


    @Query("select AVG(tra.rate)" +
            " from TraineeFeedback tra " +
            "where tra.courseRegistration.trainee.id=:traineeId")
    Double aveRatings(@Param("traineeId")Integer traineeId);

    @Query("select tr from TraineeFeedback tr where tr.courseRegistration.course.coach.id=:coachId")
    List<TraineeFeedback> findAllTraineeFeedback(@Param("coachId")Integer coachId);




}
