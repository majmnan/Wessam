package com.example.wessam.Service;

import com.example.wessam.Api.ApiException;
import com.example.wessam.DTO.IN.CoachDTOIn;
import com.example.wessam.DTO.IN.GymDTOIn;
import com.example.wessam.DTO.IN.TraineeDTOIn;
import com.example.wessam.DTO.OUT.CoachDTOOut;
import com.example.wessam.DTO.OUT.CoachDashboardDTOOut;
import com.example.wessam.DTO.OUT.GymDTOOut;
import com.example.wessam.DTO.OUT.TraineeDTOOut;
import com.example.wessam.Model.*;
import com.example.wessam.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachService {

    private final SportRepository sportRepository;
    private final CoachRepository coachRepository;
    private final CourseRepository courseRepository;
    private final TraineeFeedbackRepository traineeFeedbackRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final CourseReviewRepository courseReviewRepository;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final AiService aiService;

    //Auth: any
    public List<CoachDTOOut> getCoaches() {
        List<Coach> coaches = coachRepository.findAll();
        List<CoachDTOOut> coachDTOOuts = new ArrayList<>();
        for (Coach c : coaches) {
            coachDTOOuts.add(new CoachDTOOut(
                    c.getName(),
                    c.getPhoneNumber(),
                    c.getBirthDate(),
                    c.getYearsOfExperience(),
                    c.getSport().getName()
            ));
        }
        return coachDTOOuts;
    }

    //Auth: coach
    public void register(CoachDTOIn coachDTOIn){
        User user=new User(coachDTOIn.getUsername(), passwordEncoder.encode(coachDTOIn.getPassword()), "COACH");
        authRepository.save(user);
        Sport sport = sportRepository.findSportById(coachDTOIn.getSportId());
        Coach coach=new Coach(null, coachDTOIn.getName(), coachDTOIn.getPhoneNumber(), coachDTOIn.getBirthDate(),coachDTOIn.getYearsOfExperience(),"InActive",user,null,null,sport);
        coachRepository.save(coach);
    }

    //Auth: gym
    public void activateCoach(Integer gymId, Integer coachId){
        Coach coach = coachRepository.findCoachById(coachId);
        if(coach == null)
            throw new ApiException("coach not found");

        if(!coach.getBranch().getGym().getId().equals(gymId))
            throw new ApiException("unAuthorized");

        if(coach.getStatus().equals("Active"))
            throw new ApiException("coach already active");

        coach.setStatus("Active");
    }

    //Auth: coach
    public void updateCoach(Integer id, CoachDTOIn coachDTOIn) {
        Coach coach =coachRepository.findCoachById(id);
        if(coach ==null){
            throw new ApiException("Coach is not found");
        }
        User user=coach.getUser();

        coach.setName(coachDTOIn.getName());
        coach.setPhoneNumber(coachDTOIn.getPhoneNumber());
        coach.setBirthDate(coachDTOIn.getBirthDate());
        coach.setYearsOfExperience(coachDTOIn.getYearsOfExperience());
        user.setUsername(coachDTOIn.getUsername());
        user.setPassword(passwordEncoder.encode(coachDTOIn.getPassword()));
        authRepository.save(user);
        coachRepository.save(coach);
    }

    //Auth: coach
    public void deleteCoach(Integer id){
        Coach coach =coachRepository.findCoachById(id);
        coachRepository.delete(coach);
    }


    public CoachDashboardDTOOut getCoachDashboard(Integer coachId){
        Coach coach=coachRepository.findCoachById(coachId);
        if(coach ==null){
            throw new ApiException("Coach is not found");
        }
        Integer coursesCount=courseRepository.coachCount(coachId);
        Integer traineeCount=courseRegistrationRepository.TraineeCount(coachId);
        Double averatings=traineeFeedbackRepository.aveRatings(coachId);

        return new CoachDashboardDTOOut(coursesCount,
                traineeCount,
                averatings,
                coach.getYearsOfExperience());

    }

    public Double getaveCoachRatings(Integer coachId){
        Coach coach=coachRepository.findCoachById(coachId);
        if(coach ==null){
            throw new ApiException("Coach is not found");
        }
        Double ave=traineeFeedbackRepository.aveRatings(coachId);
        return ave;
    }

    public Integer getCoachTotalTainees(Integer coachId){
        Coach coach=coachRepository.findCoachById(coachId);
        if(coach ==null){
            throw new ApiException("Coach is not found");
        }
        Integer traineeCount=courseRegistrationRepository.TraineeCount(coachId);
        return  traineeCount;
    }

    public Integer getCoachTotalCourses(Integer coachId){
        Coach coach=coachRepository.findCoachById(coachId);
        if(coach ==null){
            throw new ApiException("Coach is not found");
        }
        Integer coursesCount=courseRepository.coachCount(coachId);
        return coursesCount;
    }

    public String coachFeedbackAiByCourse( Integer courseId,Integer coachId) {
        Coach coach=coachRepository.findCoachById(coachId);
        Course course = courseRepository.findCourseById(courseId);
        if (course == null || coach==null) {
            throw new ApiException("course or coach not found");
        }
        List<CourseReview> reviews=courseReviewRepository.fiindAllReviewByCourse(courseId);
        String prompt =
                "You are an AI specialized in coach evaluation and sentiment analysis.\n\n" +

                        "Course Name: " + course.getName() + "\n\n" +

                        "Instructions:\n" +
                        "- Analyze the coach quality based ONLY on the trainee course reviews provided.\n" +
                        "- Be objective, concise, and practical.\n\n" +

                        "Tasks:\n" +
                        "1. Determine the overall sentiment of the coach (Positive / Neutral / Negative).\n" +
                        "2. Summarize the overall sentiment in ONE clear sentence.\n" +
                        "3. List the main strengths of the coach mentioned by trainees.\n" +
                        "4. List the most common weaknesses or issues (if any).\n" +
                        "5. Provide exactly 3 actionable suggestions to improve the coach quality.\n\n" +

                        "Trainee Reviews:\n" +
                        reviews;

        return aiService.chat(prompt);
    }

    //Auth: any
    //get active coaches by gym

    //Auth: any
    //get active coaches by branch

    //Auth: gym
    //get InActive coaches by gym

    //Auth: coach
    //transfer branch request

    //Auth: Gym
    //accept transfer branch

}
