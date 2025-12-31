package com.example.wessam.Service;


import com.example.wessam.Api.ApiException;
import com.example.wessam.DTO.IN.CourseDTOIn;
import com.example.wessam.DTO.OUT.CourseDTOOut;
import com.example.wessam.Model.Coach;
import com.example.wessam.Model.Course;
import com.example.wessam.Model.Trainee;
import com.example.wessam.Repository.*;
import com.example.wessam.DTO.OUT.RecommendedDTOOut;
import com.example.wessam.DTO.OUT.TopCoursesDTOOut;
import com.example.wessam.Model.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CoachRepository coachRepository;
    private final TraineeRepository traineeRepository;
    private final CourseReviewRepository courseReviewRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final AiService aiService;
    private final GymRepository gymRepository;
    private final ModelMapper mapper;
    private final N8nService n8nService;

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
        Gym gym =  gymRepository.findGymById(gymId);
        if(gym == null)
            throw new ApiException("gym not found");
        if(!gym.getStatus().equals("Active"))
            throw new ApiException("gym is inactive yet");
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
        Gym gym =  gymRepository.findGymById(gymId);
        if(gym == null)
            throw new ApiException("gym not found");
        if(!gym.getStatus().equals("Active"))
            throw new ApiException("gym is inactive yet");
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

    //Auth: any
    public List<CourseDTOOut> getCoursesByLevel(String level){
        List<Course> nextCourses = courseRepository.findCoursesByEntryLevelAndStartDateAfter(level, LocalDate.now());
        return nextCourses.stream().map(c -> mapper.map(c, CourseDTOOut.class)).toList();
    }

    //Auth: trainee
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

    //Auth: any
    public List<TopCoursesDTOOut> getTopCourses(){
        List<Course> courses = courseRepository.findAll();
        List<TopCoursesDTOOut> top = new ArrayList<>();
        for(Course c:courses){
            Integer traineeCount=courseRegistrationRepository.TraineeCount(c.getId());
            Double avgRating=courseReviewRepository.aveRatings(c.getId());
            top.add(new TopCoursesDTOOut(
                    c.getName(),
                    c.getCoach().getName(),
                    traineeCount,
                    avgRating
            ));
        }
        return top.stream().sorted(Comparator.comparing(TopCoursesDTOOut::getTraineesCount).reversed()).limit(5).toList();
    }


    //Auth: Trainee
    public List<RecommendedDTOOut> getRecomendedCourses(Integer traineeId, Integer sportId){
        Trainee trainee=traineeRepository.findTraineeById(traineeId);
        if (trainee == null) {
            throw new ApiException("Trainee not found");
        }
        String level=trainee.getLevel();
        LocalDate today=LocalDate.now();
        List<Course> courses=courseRepository.findCoursesBySports(sportId,level,today);
        List<RecommendedDTOOut> recommendedDTOOuts=new ArrayList<>();
        for (Course c : courses) {
            recommendedDTOOuts.add(new RecommendedDTOOut(c.getName(),
                    c.getCoach().getName(),
                    c.getEntryLevel(),
                    c.getStartDate()
            ));
        }
        return recommendedDTOOuts;
    }

    //Auth: any
    public List<Course> getCoursesByStartDateRange(Integer sportId, LocalDate startDate, LocalDate endDate) {
        return courseRepository.findCoursesByDate(sportId, startDate, endDate);
    }

    //Auth: any
    public String courseFeedbackAi( Integer courseId) {
        Course course = courseRepository.findCourseById(courseId);
        if (course == null) {
            throw new ApiException("course not found");
        }
        List<CourseReview> reviews=courseReviewRepository.findAllReviewByCourse(courseId);
        String prompt =
                "You are an AI specialized in course evaluation and sentiment analysis.\n\n" +

                        "Course Name: " + course.getName() + "\n\n" +

                        "Instructions:\n" +
                        "- Analyze the course quality based ONLY on the trainee reviews provided.\n" +
                        "- Be objective, concise, and practical.\n\n" +

                        "Tasks:\n" +
                        "1. Determine the overall sentiment of the course (Positive / Neutral / Negative).\n" +
                        "2. Summarize the overall sentiment in ONE clear sentence.\n" +
                        "3. List the main strengths of the course mentioned by trainees.\n" +
                        "4. List the most common weaknesses or issues (if any).\n" +
                        "5. Provide exactly 3 actionable suggestions to improve the course quality.\n\n" +

                        "Trainee Reviews:\n" +
                        reviews;

        return aiService.callAi(prompt);
    }

    //Auth: any
    public List<Course> getUpcomingCourses(){
        LocalDate today=LocalDate.now();
        return courseRepository.findCoursesByStartDate(today);
    }


}
