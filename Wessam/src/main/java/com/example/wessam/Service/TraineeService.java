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
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.Period;
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
            traineeDTOOuts.add(new TraineeDTOOut(t.getUser().getUsername(),t.getBirthDate(),t.getGender(),t.getHeight(),t.getWeight(),t.getLevel(),t.getEmail()));
        }
        return traineeDTOOuts;
    }

    //Auth: any
    public void register(TraineeDTOIn traineeDTOIn){
        User user = new User(traineeDTOIn.getUsername(),passwordEncoder.encode(traineeDTOIn.getPassword()),"TRAINEE");
        authRepository.save(user);
        Trainee trainee = new Trainee(null,traineeDTOIn.getBirthDate(), traineeDTOIn.getGender(), traineeDTOIn.getHeight(), traineeDTOIn.getWeight(),traineeDTOIn.getLevel(),traineeDTOIn.getEmail(),user,traineeDTOIn.getName(),null,null,null,null);
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

        return aiService.callAi(prompt);     }

    //Auth: Trainee
    public JsonNode GetDayNutrition(Integer traineeId){
        Trainee trainee = traineeRepository.findTraineeById(traineeId);
        String prompt=
                """
            I need a personalized nutrition plan for a %d-year-old %s who practices %s at %s level.
            They weigh %d kg and are %d cm tall.
            Based on their activity level and goals, provide a daily caloric intake with a macronutrient breakdown (protein, carbs, fats).
            Include a meal plan for breakfast, lunch, snack, dinner, and post-workout, with food items and their respective quantities.
            Format the response in JSON with the following structure:
            
            return exact json as below (don't put any thing else and don't add character like `):
            {
                "calories_per_day": <total_calories>,
                "macronutrients": {
                    "protein": {
                        "amount": <grams>,
                        "unit": "grams"
                    },
                    "carbs": {
                        "amount": <grams>,
                        "unit": "grams"
                    },
                    "fats": {
                        "amount": <grams>,
                        "unit": "grams"
                    }
                },
                "meal_plan": [
                    {
                        "meal": "Breakfast",
                        "items": [
                            {"name": "<food_name>", "quantity": "<amount>"}
                        ]
                    },
                    {
                        "meal": "Lunch",
                        "items": [
                            {"name": "<food_name>", "quantity": "<amount>"}
                        ]
                    },
                    {
                        "meal": "Snack",
                        "items": [
                            {"name": "<food_name>", "quantity": "<amount>"}
                        ]
                    },
                    {
                        "meal": "Dinner",
                        "items": [
                            {"name": "<food_name>", "quantity": "<amount>"}
                        ]
                    },
                    {
                        "meal": "Post-Workout",
                        "items": [
                            {"name": "<food_name>", "quantity": "<amount>"}
                        ]
                    }
                ],
                "notes": "Include any relevant advice or guidance for maintaining a balanced diet."
            }
        """.formatted(Period.between(trainee.getBirthDate(),LocalDate.now()).getYears(), trainee.getGender(), trainee.getSport().getName(), trainee.getLevel(), trainee.getWeight(), trainee.getHeight());

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(
                aiService.callAi(prompt)
        );
    }
}