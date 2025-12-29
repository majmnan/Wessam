package com.example.wessam.Service;

import com.example.wessam.Api.ApiException;
import com.example.wessam.DTO.IN.GymDTOIn;
import com.example.wessam.Model.Gym;
import com.example.wessam.Model.Trainee;
import com.example.wessam.Model.User;
import com.example.wessam.Repository.AuthRepository;
import com.example.wessam.Repository.GymRepository;
import com.example.wessam.Repository.TraineeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GymService {

    private final PasswordEncoder passwordEncoder;
    private final GymRepository gymRepository;
    private final AuthRepository authRepository;
    private final AiService aiService;
    private final EmailService emailService;
    private final TraineeRepository traineeRepository;

    //Auth: any
    public void register(GymDTOIn dto){
        User user = authRepository.save(new User(dto.getUsername(), passwordEncoder.encode(dto.getPassword()), "GYM"));
        gymRepository.save(new Gym(null,  dto.getName(), dto.getBusinessCertificateId(), "InActive", dto.getDescription(), null,user));
    }

    //Auth: Admin
    public void activateGym(Integer gymId){
        Gym gym = gymRepository.findGymById(gymId);
        if(gym.getStatus().equals("Active"))
            throw new ApiException("gym is already active");
        gym.setStatus("Active");
        gymRepository.save(gym);
    }

    //Auth: gym
    public void updateGym(Integer gymId, GymDTOIn dto){
        Gym gym = gymRepository.findGymById(gymId);
        User user = gym.getUser();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        authRepository.save(user);

        gym.setName(dto.getName());
        gym.setDescription(dto.getDescription());
    }

    //Auth: gym
    public void deleteGym(Integer gymId){
        authRepository.deleteById(gymId);
    }


    //trainee auth
    public void reportIncident(Integer traineeId, String message) {
        Trainee trainee = traineeRepository.findTraineeById(traineeId);
        if (message == null)
            throw new ApiException("Message cannot be empty");

        String prompt = "analyze the following incident report from a trainee in the gym " +
                "classify the priority as exactly one of these words: LOW, MEDIUM, HIGH" +
                "Then provide a one-sentence recommended action for the admin. " +
                "Format your answer exactly like this: 'PRIORITY: [Level] - [Action]'. " +
                "the report: " + message;
        String aiAnalysis = aiService.chat(prompt);
        String subject;
        if (aiAnalysis.toUpperCase().contains("HIGH")) {
            subject = "HIGH priority incident report";
        } else if (aiAnalysis.toUpperCase().contains("MEDIUM")) {
            subject = "MEDIUM priority incident report";
        } else {
            subject = "LOW priority incident  report";
        }

        String emailBody = "Reporter: " + trainee.getUser().getUsername() + " ID: " + trainee.getId() +
                "\n---  ORIGINAL REPORT ---\n" +message+
                "\n--- AI ANALYSIS & RECOMMENDATION ---\n" +aiAnalysis +
                "\nPlease take appropriate action.";

        String adminEmail = "alshahrani996655@gmail.com";
        emailService.sendEmail(adminEmail, subject, emailBody);
    }

    //Auth: Admin
    //get all gyms

    //Auth: Admin
    //get InActive gyms

    //Auth: any
    //get active gyms
}
