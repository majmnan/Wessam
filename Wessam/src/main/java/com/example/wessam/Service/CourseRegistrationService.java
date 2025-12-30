package com.example.wessam.Service;

import com.example.wessam.Api.ApiException;
import com.example.wessam.DTO.OUT.CourseRegistrationDTOOut;
import com.example.wessam.DTO.OUT.TraineeCountDTOOut;
import com.example.wessam.Model.Course;
import com.example.wessam.Model.CourseRegistration;
import com.example.wessam.Model.RegisteredTournament;
import com.example.wessam.Model.Trainee;
import com.example.wessam.Repository.CourseRepository;
import com.example.wessam.Repository.CourseRegistrationRepository;
import com.example.wessam.Repository.TraineeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseRegistrationService {

    private final CourseRegistrationRepository courseRegistrationRepository;
    private final CourseRepository courseRepository;
    private final TraineeRepository traineeRepository;


    //Auth: Coach
    public List<CourseRegistrationDTOOut> getRegistrationsOfACourse(Integer coachId, Integer courseId){
        Course course = courseRepository.findCourseById(courseId);
        if(!course.getCoach().getId().equals(coachId))
            throw new ApiException("unAuthorized");

        return courseRegistrationRepository.findCourseRegistrationByCourseId(courseId)
                .stream().map( rc ->
                        new CourseRegistrationDTOOut(rc.getTrainee().getName(), rc.getCourse().getName())
                ).toList();
    }

    //Auth: Trainee
    public void registerInCourse(Integer traineeId, Integer courseId){
        Course course = courseRepository.findCourseById(courseId);
        if(course == null)
            throw new ApiException("course not found");

        Trainee trainee = traineeRepository.findTraineeById(traineeId);

        if(!course.getEntryLevel().equals(trainee.getLevel()))
            throw new ApiException("this trainee is not in the same level of the course");

        courseRegistrationRepository.save(new CourseRegistration(null,course,trainee,null,null));
    }

    //Auth: Trainee
    public void deleteRegistration(Integer traineeId, Integer registeredCourseId){
        CourseRegistration courseRegistration = courseRegistrationRepository.findCourseRegistrationById(registeredCourseId);
        if(courseRegistration == null)
            throw new ApiException("no registration found");

        if(!courseRegistration.getTrainee().getId().equals(traineeId))
            throw new ApiException("unAuthorized");

        courseRegistrationRepository.delete(courseRegistration);
    }

    public List<Course> getCoursesRegestered(Integer traineeId) {
        Trainee trainee = traineeRepository.findTraineeById(traineeId);
        if (trainee == null) {
            throw new ApiException("Trainee not found");
        }
        return courseRegistrationRepository.findRegestedCourses(traineeId);
    }


    public TraineeCountDTOOut courseNumOfTrainees(Integer id){
        CourseRegistration courseRegistration = courseRegistrationRepository.findCourseRegistrationById(id);
        if(courseRegistration == null){
            throw new ApiException("no registration found");
        }
        Integer traineeCount= courseRegistrationRepository.TraineeCourseCount(id);
        return new TraineeCountDTOOut(traineeCount);
    }
}
