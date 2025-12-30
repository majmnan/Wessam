package com.example.wessam.Service;


import com.example.wessam.Api.ApiException;
import com.example.wessam.DTO.IN.CourseDTOIn;
import com.example.wessam.DTO.IN.N8nPdfCertGenDTOIn;
import com.example.wessam.DTO.OUT.CourseDTOOut;
import com.example.wessam.DTO.OUT.N8nPdfCertGenDtoOUT;
import com.example.wessam.Model.Coach;
import com.example.wessam.Model.Course;
import com.example.wessam.Model.CourseRegistration;
import com.example.wessam.Model.Trainee;
import com.example.wessam.Repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CoachRepository coachRepository;
    private final GymRepository gymRepository;
    private final ModelMapper mapper;
    private final TraineeRepository traineeRepository;
    private final N8nService n8nService;
    private final CourseRegistrationRepository courseRegistrationRepository;

    //Auth: any
    public List<CourseDTOOut> getAllCourses() {
        return
                courseRepository.findAll().stream().map(c -> {
                    CourseDTOOut dto = mapper.map(c, CourseDTOOut.class);
                    dto.setCoachName(c.getCoach().getName());
                    dto.setLocation(c.getCoach().getBranch().getLocation());
                    dto.setGymName(c.getCoach().getBranch().getGym().getName());
                    return dto;
                }).toList();
    }

    //Auth: Gym
    public void addCourse(Integer gymId , CourseDTOIn dto) {
        Coach coach = coachRepository.findCoachById(dto.getCoachId());

        if (coach == null) {
            throw new ApiException("Coach not found");
        }
        if(!coach.getBranch().getGym().getId().equals(gymId)){
            throw new ApiException("unAuthorized");
        }
        Course course = new Course(null, dto.getName(), dto.getPrice(),dto.getEntryLevel(), dto.getStartDate(), dto.getEndDate(),null, coach);
        courseRepository.save(course);
    }

    //Auth: gym
    public void updateCourse(Integer gymId, Integer courseId, CourseDTOIn dto){
        Course oldCourse = courseRepository.findCourseById(courseId);
        if (oldCourse == null) {
            throw new ApiException("course not found");
        }
        if (!oldCourse.getCoach().getBranch().getGym().getId().equals(gymId)) {
            throw new ApiException("unAuthorized");
        }
        Coach coach = coachRepository.findCoachById(dto.getCoachId());
        if (coach == null) {
            throw new ApiException("coach not found");
        }
        if (coach.getBranch() == null || !coach.getBranch().getGym().getId().equals(gymId)) {
            throw new ApiException("The selected coach does not belong to your gym");
        }
        oldCourse.setName(dto.getName());
        oldCourse.setEntryLevel(dto.getEntryLevel());
        oldCourse.setStartDate(dto.getStartDate());
        oldCourse.setEndDate(dto.getEndDate());
        oldCourse.setCoach(coach);
        oldCourse.setPrice(dto.getPrice());
        courseRepository.save(oldCourse);
    }

    //Auth: gym
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

    //trainee auth  //Should review: Auth all ,get by lvl
    public List<CourseDTOOut> nextLevelCourses(Integer traineeId) {
        Trainee trainee = traineeRepository.findTraineeById(traineeId);
        if (trainee == null)
            throw new ApiException("Trainee not found");

        String currentLevel = trainee.getLevel();
        String nextLevel;

        if ("beginner".equalsIgnoreCase(currentLevel))
            nextLevel = "intermediate";
        else if ("intermediate".equalsIgnoreCase(currentLevel))
            nextLevel = "advanced";
        else return List.of();

        List<Course> nextCourses = courseRepository.findCoursesByEntryLevelAndStartDateAfter(nextLevel, LocalDate.now());
        return nextCourses.stream().map(c -> mapper.map(c, CourseDTOOut.class)).toList();
    }

}
