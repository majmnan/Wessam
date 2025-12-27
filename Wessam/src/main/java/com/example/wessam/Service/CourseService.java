package com.example.wessam.Service;


import com.example.wessam.Api.ApiException;
import com.example.wessam.Model.Coach;
import com.example.wessam.Model.Course;
import com.example.wessam.Model.Gym;
import com.example.wessam.Repository.CoachRepository;
import com.example.wessam.Repository.CourseRepository;
import com.example.wessam.Repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CoachRepository coachRepository;
    private final GymRepository gymRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public void addCourse(Integer gymId ,Course course, Integer coachId) {
        Coach coach = coachRepository.findCoachById(coachId);
        Gym gym = gymRepository.findGymById(gymId);

        if (coach == null) {
            throw new ApiException("Coach not found");
        }
        if(!(gym==coach.getBranch().getGym())){
            throw new ApiException("Coach is not in the same gym");
        }
        course.setCoach(coach);
        courseRepository.save(course);
    }

    public void updateCourse(Integer gymId,Course course,Integer courseId,Integer coachId){
        Course oldCourse = courseRepository.findCourseById(courseId);
        if (oldCourse == null) {
            throw new ApiException("course not found");
        }
        if (!oldCourse.getCoach().getBranch().getGym().getId().equals(gymId)) {
            throw new ApiException("you do not have permission to update this course");
        }
        Coach coach = coachRepository.findCoachById(coachId);
        if (coach == null) {
            throw new ApiException("coach not found");
        }
        if (coach.getBranch() == null || !coach.getBranch().getGym().getId().equals(gymId)) {
            throw new ApiException("The selected coach does not belong to your gym");
        }
        oldCourse.setEntryLevel(course.getEntryLevel());
        oldCourse.setStartDate(course.getStartDate());
        oldCourse.setEndDate(course.getEndDate());
        oldCourse.setCoach(coach);
        courseRepository.save(oldCourse);
    }
    public void deleteCourse(Integer gymId, Integer courseId) {
        Course course = courseRepository.findCourseById(courseId);
        if (course == null) {
            throw new ApiException("course not found");
        }
        if (!course.getCoach().getBranch().getGym().getId().equals(gymId)) {
            throw new ApiException("you do not have permission to delete this course");
        }
        courseRepository.delete(course);
    }

}
