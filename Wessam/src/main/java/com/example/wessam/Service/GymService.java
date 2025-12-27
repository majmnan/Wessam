package com.example.wessam.Service;

import com.example.wessam.Api.ApiException;
import com.example.wessam.DTO.IN.GymDTOIn;
import com.example.wessam.DTO.IN.TraineeDTOIn;
import com.example.wessam.DTO.OUT.GymDTOOut;
import com.example.wessam.DTO.OUT.TraineeDTOOut;
import com.example.wessam.Model.Gym;
import com.example.wessam.Model.Trainee;
import com.example.wessam.Model.User;
import com.example.wessam.Repository.AuthRepository;
import com.example.wessam.Repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GymService {

    private final GymRepository gymRepository;
    private final AuthRepository authRepository;


    public List<GymDTOOut> getGyms(){
        List<Gym> gyms=gymRepository.findAll();
        List<GymDTOOut> gymDTOOuts=new ArrayList<>();
        for(Gym g: gyms){
            String username = g.getUser() != null ? g.getUser().getUsername() : null;
            gymDTOOuts.add(new GymDTOOut(username,
                    g.getBusinuissCertificateId(),
                    g.getStatus(),
                    g.getDescription()));

        }
        return gymDTOOuts;
    }


    public void addGym(GymDTOIn gymDTOIn){
        Gym gym=new Gym();
        String hashed=new BCryptPasswordEncoder().encode(gymDTOIn.getPassword());
        User user=new User();
        user.setUsername(gymDTOIn.getUsername());
        user.setPassword(hashed);
        user.setRole("GYM");
        gym.setUser(user);
        gym.setDescription(gymDTOIn.getDescription());
        gym.setStatus(gymDTOIn.getStatus());
        gym.setBusinuissCertificateId(gymDTOIn.getBusinuissCertificateId());
        gymRepository.save(gym);
    }


    public void updateGym(Integer id, GymDTOIn gymDTOIn) {
        Gym gym =gymRepository.findGymById(id);
        if(gym ==null){
            throw new ApiException("Gym is not found");
        }
        String hashed=new BCryptPasswordEncoder().encode(gymDTOIn.getPassword());

        gym.setBusinuissCertificateId(gymDTOIn.getBusinuissCertificateId());
        gym.setStatus(gymDTOIn.getStatus());
        gym.setDescription(gymDTOIn.getDescription());
        User user=gym.getUser();
        user.setUsername(gymDTOIn.getUsername());
        user.setPassword(hashed);
        authRepository.save(user);
        gymRepository.save(gym);
    }

    public void deleteGym(Integer id){
        Gym gym =gymRepository.findGymById(id);
        if(gym ==null){
            throw new ApiException("Gym is not found");
        }
        gymRepository.delete(gym);
    }


}
