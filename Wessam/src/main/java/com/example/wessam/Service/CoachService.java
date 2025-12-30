package com.example.wessam.Service;

import com.example.wessam.Api.ApiException;
import com.example.wessam.DTO.IN.CoachDTOIn;
import com.example.wessam.DTO.IN.GymDTOIn;
import com.example.wessam.DTO.IN.TraineeDTOIn;
import com.example.wessam.DTO.OUT.CoachDTOOut;
import com.example.wessam.DTO.OUT.GymDTOOut;
import com.example.wessam.DTO.OUT.TraineeDTOOut;
import com.example.wessam.Model.*;
import com.example.wessam.Repository.AuthRepository;
import com.example.wessam.Repository.BranchRepository;
import com.example.wessam.Repository.CoachRepository;
import com.example.wessam.Repository.SportRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;
    private final BranchRepository branchRepository;


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
        Branch branch = branchRepository.findBranchById(coachDTOIn.getBranchId());
        Coach coach=new Coach(null, coachDTOIn.getName(), coachDTOIn.getPhoneNumber(), coachDTOIn.getBirthDate(),coachDTOIn.getYearsOfExperience(),"InActive",user,branch,null,sport);
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

    //gym auth
    public List<CoachDTOOut> getAvailableCoaches(LocalDate targetDate) {
        List<Coach> allCoaches = coachRepository.findAll();
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
        return availableCoaches.stream().map(c -> mapper.map(c, CoachDTOOut.class)).toList();
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
