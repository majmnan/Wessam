package com.example.wessam.Service;

import com.example.wessam.Api.ApiException;
import com.example.wessam.DTO.IN.CoachDTOIn;
import com.example.wessam.DTO.OUT.CoachDTOOut;
import com.example.wessam.DTO.OUT.CoachDashboardDTOOut;
import com.example.wessam.Model.*;
import com.example.wessam.Repository.*;
import com.example.wessam.Repository.AuthRepository;
import com.example.wessam.Repository.BranchRepository;
import com.example.wessam.Repository.CoachRepository;
import com.example.wessam.Repository.SportRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private final ModelMapper mapper;
    private final BranchRepository branchRepository;
    private final EmailService emailService;
    private final N8nService n8nService;
    private final AiService aiService;
    private final GymRepository gymRepository;

    //Auth: any
    public List<CoachDTOOut> getCoaches() {
        List<Coach> coaches = coachRepository.findAll();
        List<CoachDTOOut> coachDTOOuts = new ArrayList<>();
        for (Coach c : coaches) {
            coachDTOOuts.add(new CoachDTOOut(
                    c.getId(),
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
        Branch branch = branchRepository.findBranchById(coachDTOIn.getBranchId());
        Coach coach=new Coach(null, coachDTOIn.getName(), coachDTOIn.getPhoneNumber(), coachDTOIn.getBirthDate(),coachDTOIn.getYearsOfExperience(),coachDTOIn.getEmail(),"InActive",user,branch,null,sport);
        coachRepository.save(coach);
        emailService.sendEmail(
                coach.getEmail(),
                "مقابلة شخصية لشاغر مدرب في نادي"+coach.getBranch().getGym().getName(),
                "دعوة للأنضمام لمقابلة شخصية مجدولة " +
                        "الوقت:" + " الساعة 1 مساءا\n" +
                        "\n" +
                        "رابط المقابلة الشخصية:\n" +
                        n8nService.triggerZoom(coach.getId()).getJoin_url()+
                        "\n" +
                        "مع أطيب التحيات،\n" +
                        "فريق  "+coach.getBranch().getGym().getName()
        );
    }

    //Auth: gym
    public void activateCoach(Integer gymId, Integer coachId){
        Gym gym =  gymRepository.findGymById(gymId);
        if(gym == null)
            throw new ApiException("gym not found");
        if(!gym.getStatus().equals("Active"))
            throw new ApiException("gym is inactive yet");
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
        coach.setEmail(coachDTOIn.getEmail());
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

    //gym auth
    public List<CoachDTOOut> getAvailableCoaches(Integer gymId, LocalDate targetDate) {
        List<Coach> allCoaches = coachRepository.findCoachByBranch_Gym_Id(gymId);
        List<Coach> availableCoaches = new ArrayList<>();

        for (Coach coach : allCoaches) {
            boolean isBusy = false;
            for (Course course : coach.getCourses()) {
                if (!targetDate.isBefore(course.getStartDate()) && !targetDate.isAfter(course.getEndDate())) {
                    isBusy = true;
                    break;
                }
            }
            if (!isBusy) {
                availableCoaches.add(coach);
            }
        }
        return availableCoaches.stream().map(c -> {
            CoachDTOOut dto = mapper.map(c, CoachDTOOut.class);
            dto.setSportName(c.getSport().getName());
            return dto;
        }).toList();
    }

    //Auth: coach
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

    //Auth: any
    public Double getAvgCoachRatings(Integer coachId){
        Coach coach=coachRepository.findCoachById(coachId);
        if(coach ==null){
            throw new ApiException("Coach is not found");
        }
        Double ave=traineeFeedbackRepository.aveRatings(coachId);
        return ave;
    }

    //Auth: any
    public Integer getCoachTotalTrainees(Integer coachId){
        Coach coach=coachRepository.findCoachById(coachId);
        if(coach ==null){
            throw new ApiException("Coach is not found");
        }
        Integer traineeCount=courseRegistrationRepository.TraineeCount(coachId);
        return  traineeCount;
    }

    //Auth: any
    public Integer getCoachTotalCourses(Integer coachId){
        Coach coach=coachRepository.findCoachById(coachId);
        if(coach ==null){
            throw new ApiException("Coach is not found");
        }
        Integer coursesCount=courseRepository.coachCount(coachId);
        return coursesCount;
    }

    //Auth: any
    public String coachFeedbackAiByCourse( Integer courseId,Integer coachId) {
        Coach coach=coachRepository.findCoachById(coachId);
        Course course = courseRepository.findCourseById(courseId);
        if (course == null || coach==null) {
            throw new ApiException("course or coach not found");
        }
        List<CourseReview> reviews=courseReviewRepository.findAllReviewByCourse(courseId);
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

        return aiService.callAi(prompt);
    }

    //Auth: any
    //get active coaches by gym

    //Auth: any
    //get active coaches by branch

    //Auth: gym
    //get InActive coaches by gym

}
