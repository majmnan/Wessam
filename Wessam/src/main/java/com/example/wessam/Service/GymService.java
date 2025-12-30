package com.example.wessam.Service;

import com.example.wessam.Api.ApiException;
import com.example.wessam.DTO.IN.CardDTOIn;
import com.example.wessam.DTO.IN.GymDTOIn;
import com.example.wessam.DTO.IN.PaymentRequestDTO;
import com.example.wessam.DTO.OUT.PaymentResponseDTO;
import com.example.wessam.Model.CourseRegistration;
import com.example.wessam.DTO.OUT.GymDTOOut;
import com.example.wessam.Model.Gym;
import com.example.wessam.Model.Trainee;
import com.example.wessam.Model.User;
import com.example.wessam.Repository.AuthRepository;
import com.example.wessam.Repository.GymRepository;
import com.example.wessam.Repository.TraineeRepository;
import io.netty.util.internal.IntegerHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GymService {

    private final PasswordEncoder passwordEncoder;
    private final GymRepository gymRepository;
    private final AuthRepository authRepository;
    private final AiService aiService;
    private final EmailService emailService;
    private final TraineeRepository traineeRepository;
    private final PaymentService paymentService;

    //Auth: any
    public void register(GymDTOIn dto){
        User user = authRepository.save(new User(dto.getUsername(), passwordEncoder.encode(dto.getPassword()), "GYM"));
        gymRepository.save(new Gym(null,  dto.getName(), dto.getBusinessCertificateId(), "InActive",null, dto.getDescription(), null,user));
    }

    //Auth: Admin
    //todo: change the logic
    public void activateGym(Integer gymId){
        Gym gym = gymRepository.findGymById(gymId);
        if(gym.getStatus().equals("Active"))
            throw new ApiException("gym is already active");
        gym.setStatus("Pending");
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
    //Auth: Any
    public List<GymDTOOut> getAllGyms() {
        return gymRepository.findAll().stream()
                .map(gym -> new GymDTOOut(gym.getName(), gym.getDescription(), gym.getSubscriptionEndDate()))
                .toList();
    }

    //Auth: gym
    public ResponseEntity<PaymentResponseDTO> subscribe(Integer gymId, CardDTOIn card, Integer months, Integer price){
        if(gymRepository.findGymById(gymId).getStatus().equals("InActive"))
            throw new ApiException("gym waits approve from admin");
        ResponseEntity<PaymentResponseDTO> response = paymentService.processPayment(new PaymentRequestDTO(card, price, "SAR", String.valueOf(gymId), "http://localhost:8080/api/v1/gym/complete-payment/"+months));
        if(!response.getStatusCode().is2xxSuccessful())
            throw new ResponseStatusException(response.getStatusCode(),response.hasBody() ? response.getBody().toString() : "");

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    public void checkPayment(Integer months, String paymentId){
        ResponseEntity<PaymentResponseDTO> response = paymentService.getPayment(paymentId);
        if("paid".equals(response.getBody().getStatus())){
            Gym gym = gymRepository.findGymById(Integer.valueOf(response.getBody().getDescription()));
            if(gym.getStatus().equals("Pending")) {
                gym.setSubscriptionEndDate(LocalDate.now().plusMonths(months));
            }
            gym.setSubscriptionEndDate(gym.getSubscriptionEndDate().plusMonths(months));
            gym.setStatus("Active");
            gymRepository.save(gym);
        }
        throw new ResponseStatusException(response.getStatusCode(),response.getBody().getSource().getMessage());
    }

    //Auth: Admin
    //get all gyms
    // Auth: Admin
    public List<GymDTOOut> getInactiveGyms() {
        return gymRepository.findAllByStatus("InActive").stream()
                .map(gym -> new GymDTOOut(gym.getName(), gym.getDescription(),gym.getSubscriptionEndDate()))
                .toList();
    }

    // Auth: Any
    public List<GymDTOOut> getActiveGyms() {
        return gymRepository.findAllByStatus("Active").stream()
                .map(gym -> new GymDTOOut(gym.getName(), gym.getDescription(),gym.getSubscriptionEndDate()))
                .toList();
    }

    //Auth: Admin
    public void deactivateGym(Integer gymId){
        Gym gym = gymRepository.findGymById(gymId);
        if(gym.getStatus().equals("InActive"))
            throw new ApiException("gym is already InActive");
        gym.setStatus("InActive");
        gymRepository.save(gym);
    }

}
