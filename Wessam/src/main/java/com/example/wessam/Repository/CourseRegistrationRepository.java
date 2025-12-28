package com.example.wessam.Repository;

import com.example.wessam.Model.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Integer> {
    CourseRegistration findCourseRegistrationById(Integer id);
    List<CourseRegistration> findCourseRegistrationByCourseId(Integer courseId);
}
