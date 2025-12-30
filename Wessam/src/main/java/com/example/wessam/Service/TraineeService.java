package com.example.wessam.Service;

import com.example.wessam.Api.ApiException;
import com.example.wessam.DTO.IN.TraineeDTOIn;
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
public class TraineeService {

    private final TraineeRepository traineeRepository;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final AiService aiService;
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final TraineeFeedbackRepository traineeFeedbackRepository;
    private final CoachRepository coachRepository;

    //Auth: Admin
    public List<TraineeDTOOut> getAllTrainees(){
        List<Trainee> trainees = traineeRepository.findAll();
        List<TraineeDTOOut> traineeDTOOuts = new ArrayList<>();
        for (Trainee t : trainees) {
            traineeDTOOuts.add(new TraineeDTOOut(t.getUser().getUsername(),t.getBirthDate(),t.getGender(),t.getHeight(),t.getWeight(),t.getLevel()));
        }
        return traineeDTOOuts;
    }

    //Auth: any
    public void register(TraineeDTOIn traineeDTOIn){
        User user = new User(traineeDTOIn.getUsername(),passwordEncoder.encode(traineeDTOIn.getPassword()),"TRAINEE");
        authRepository.save(user);
        Trainee trainee = new Trainee(null,traineeDTOIn.getBirthDate(), traineeDTOIn.getGender(), traineeDTOIn.getHeight(), traineeDTOIn.getWeight(),traineeDTOIn.getLevel(),user,traineeDTOIn.getName(),null,null,null,null);
        traineeRepository.save(trainee);
    }

    //Auth: Trainee
    public void updateTrainee(Integer traineeId,TraineeDTOIn traineeDTOIn){
        Trainee trainee = traineeRepository.findTraineeById(traineeId);
        if (trainee == null) {
            throw new ApiException("Trainee not found");
        }
        trainee.setBirthDate(traineeDTOIn.getBirthDate());
        trainee.setGender(traineeDTOIn.getGender());
        trainee.setHeight(traineeDTOIn.getHeight());
        trainee.setWeight(traineeDTOIn.getWeight());
        trainee.setLevel(traineeDTOIn.getLevel());
        User user = trainee.getUser();
        user.setUsername(traineeDTOIn.getUsername());
        user.setPassword(passwordEncoder.encode(traineeDTOIn.getPassword()));
        authRepository.save(user);
        traineeRepository.save(trainee);
    }

    //Auth: Trainee
    public void deleteTrainee(Integer id) {
        Trainee trainee = traineeRepository.findTraineeById(id);
        if (trainee == null) {
            throw new ApiException("Trainee not found");
        }
        authRepository.delete(trainee.getUser());
    }

    public String aiCoach( Integer traineeId) {
        Trainee trainee = traineeRepository.findTraineeById(traineeId);
        if (trainee == null) {
            throw new ApiException("Trainee not found");
        }
        List<Course> regested=courseRegistrationRepository.findRegestedCourses(traineeId);
        String prompt = "Hello " + trainee.getName() + "! You are a professional coach. " +
                "Provide personalized training advice for " + trainee.getName() +
                ", who is an " + trainee.getLevel() + " level athlete in " + trainee.getSport().getName() +
                ". Birth Date: " + trainee.getBirthDate()+
                ". They have attended " + regested + " courses. " +
                "Give short, practical recommendations for exercises, training focus, and next steps to improve their skills.";

        return aiService.chat(prompt);     }


    public String coachFeedbackAi( Integer traineeId,Integer coachId) {
        Coach coach=coachRepository.findCoachById(coachId);
        Trainee trainee=traineeRepository.findTraineeById(traineeId);
        if(traineeId ==null){
            throw new ApiException("Trainee or Coach is not found");
        }
        List<TraineeFeedback> feedbacks=traineeFeedbackRepository.findAllTraineeFeedback(coachId);
        String prompt =
                "You are an AI specialized in trainee performance evaluation and sentiment analysis.\n" +
                        "Analyze the following trainee feedback by this coach " + coach.getName() + ".\n\n" +

                        "Tasks:\n" +
                        "1. Perform sentiment analysis (Positive / Neutral / Negative).\n" +
                        "2. Summarize overall sentiment in one sentence.\n" +
                        "3. List key strengths mentioned for trainee.\n" +
                        "4. List common weaknesses or complaints.\n" +
                        "5. Provide 3 clear, actionable improvement suggestions.\n\n" +

                        "coach ai Feedback for trainee:\n" +
                        feedbacks;
        return aiService.chat(prompt);
    }
}