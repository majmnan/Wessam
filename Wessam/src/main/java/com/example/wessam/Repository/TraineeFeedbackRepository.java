package com.example.wessam.Repository;

import com.example.wessam.Model.TraineeFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TraineeFeedbackRepository extends JpaRepository<TraineeFeedback,Integer> {
    TraineeFeedback findTraineeFeedbackById(Integer id);
    List<TraineeFeedback> findTraineeFeedbacksByCourseRegistration_Trainee_Id(Integer courseId);
}
