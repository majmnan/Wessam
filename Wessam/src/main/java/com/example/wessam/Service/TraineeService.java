package com.example.wessam.Service;

import com.example.wessam.Api.ApiException;
import com.example.wessam.DTO.IN.TraineeDTOIn;
import com.example.wessam.DTO.OUT.TraineeDTOOut;
import com.example.wessam.Model.Trainee;
import com.example.wessam.Model.User;
import com.example.wessam.Repository.AuthRepository;
import com.example.wessam.Repository.TraineeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TraineeService {

    private final TraineeRepository traineeRepository;
    private final AuthRepository authRepository;

    public List<TraineeDTOOut> getAllTrainees(){
        List<Trainee> trainees = traineeRepository.findAll();
        List<TraineeDTOOut> traineeDTOOuts = new ArrayList<>();
        for (Trainee t : trainees) {
            traineeDTOOuts.add(new TraineeDTOOut(t.getUser().getUsername(),t.getBirthDate(),t.getGender(),t.getHeight(),t.getWeight(),t.getLevel()));
        }
        return traineeDTOOuts;
    }

    public void addTrainee(TraineeDTOIn traineeDTOIn){
        String hashed = new BCryptPasswordEncoder().encode(traineeDTOIn.getPassword());
        User user = new User(null,traineeDTOIn.getUserName(),hashed,"TRAINEE",null,null,null,null);
        authRepository.save(user);
        Trainee trainee = new Trainee(null,traineeDTOIn.getBirthDate(), traineeDTOIn.getGender(), traineeDTOIn.getHeight(), traineeDTOIn.getWeight(),traineeDTOIn.getLevel(),user,null,null,null,null);
        traineeRepository.save(trainee);
    }

    public void updateTrainee(Integer traineeId,TraineeDTOIn traineeDTOIn){
        Trainee trainee = traineeRepository.findTraineeById(traineeId);
        if (trainee == null) {
            throw new ApiException("Trainee not found");
        }
        String hashed = new BCryptPasswordEncoder().encode(traineeDTOIn.getPassword());
        trainee.setBirthDate(traineeDTOIn.getBirthDate());
        trainee.setGender(traineeDTOIn.getGender());
        trainee.setHeight(traineeDTOIn.getHeight());
        trainee.setWeight(traineeDTOIn.getWeight());
        trainee.setLevel(traineeDTOIn.getLevel());
        User user = trainee.getUser();
        user.setUsername(traineeDTOIn.getUserName());
        user.setPassword(hashed);
        authRepository.save(user);
        traineeRepository.save(trainee);
    }
    public void deleteTrainee(Integer id) {
        Trainee trainee = traineeRepository.findTraineeById(id);
        if (trainee == null) {
            throw new ApiException("Trainee not found");
        }
        authRepository.delete(trainee.getUser());
    }
}