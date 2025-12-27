package com.example.wessam.Service;

import com.example.wessam.Api.ApiException;
import com.example.wessam.DTO.IN.GymDTOIn;
import com.example.wessam.Model.Gym;
import com.example.wessam.Model.User;
import com.example.wessam.Repository.AuthRepository;
import com.example.wessam.Repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jackson.autoconfigure.JacksonProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;

@Service
@RequiredArgsConstructor
public class GymService {

    private final PasswordEncoder passwordEncoder;
    private final GymRepository gymRepository;
    private final AuthRepository authRepository;

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
}
