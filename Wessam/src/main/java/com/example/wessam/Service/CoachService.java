package com.example.wessam.Service;

import com.example.wessam.Api.ApiException;
import com.example.wessam.DTO.IN.CoachDTOIn;
import com.example.wessam.DTO.IN.GymDTOIn;
import com.example.wessam.DTO.IN.TraineeDTOIn;
import com.example.wessam.DTO.OUT.CoachDTOOut;
import com.example.wessam.DTO.OUT.GymDTOOut;
import com.example.wessam.DTO.OUT.TraineeDTOOut;
import com.example.wessam.Model.Coach;
import com.example.wessam.Model.Gym;
import com.example.wessam.Model.Trainee;
import com.example.wessam.Model.User;
import com.example.wessam.Repository.AuthRepository;
import com.example.wessam.Repository.CoachRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachService {



    private final CoachRepository coachRepository;
    private final AuthRepository authRepository;

    public List<CoachDTOOut> getCoaches() {
        List<Coach> coaches = coachRepository.findAll();
        List<CoachDTOOut> coachDTOOuts = new ArrayList<>();
        for (Coach c : coaches) {
            String username = c.getUser() != null ? c.getUser().getUsername() : null;
            coachDTOOuts.add(new CoachDTOOut(
                    username,
                    c.getPhoneNumber(),
                    c.getBirthDate(),
                    c.getYearsOfExperience()
            ));
        }
        return coachDTOOuts;
    }


    public void addCoach(CoachDTOIn coachDTOIn){
        Coach coach=new Coach();
        String hashed=new BCryptPasswordEncoder().encode(coachDTOIn.getPassword());
        User user=new User();
        user.setUsername(coachDTOIn.getUsername());
        user.setPassword(hashed);
        user.setRole("COACH");
        coach.setUser(user);
        coach.setPhoneNumber(coachDTOIn.getPhoneNumber());
        coach.setBirthDate(coachDTOIn.getBirthDate());
        coach.setYearsOfExperience(coachDTOIn.getYearsOfExperience());
        coachRepository.save(coach);
    }


    public void updateCoach(Integer id, CoachDTOIn coachDTOIn) {
        Coach coach =coachRepository.findCoachById(id);
        if(coach ==null){
            throw new ApiException("Coach is not found");
        }
        String hashed=new BCryptPasswordEncoder().encode(coachDTOIn.getPassword());

        coach.setPhoneNumber(coachDTOIn.getPhoneNumber());
        coach.setBirthDate(coachDTOIn.getBirthDate());
        coach.setYearsOfExperience(coachDTOIn.getYearsOfExperience());
        User user=coach.getUser();
        user.setUsername(coachDTOIn.getUsername());
        user.setPassword(hashed);
        authRepository.save(user);
        coachRepository.save(coach);
    }

    public void deleteCoach(Integer id){
        Coach coach =coachRepository.findCoachById(id);
        if(coach ==null){
            throw new ApiException("Coach is not found");
        }
        coachRepository.delete(coach);
    }


}
