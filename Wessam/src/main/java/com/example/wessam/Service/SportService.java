package com.example.wessam.Service;

import com.example.wessam.Api.ApiException;
import com.example.wessam.Model.Sport;
import com.example.wessam.Repository.SportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SportService {

    private final SportRepository sportRepository;
    private final AiService aiService;

    //Auth: all
    public List<Sport> getSports() {
        return sportRepository.findAll();
    }

    //Auth: Admin
    public void addSport(Sport sport) {
        sportRepository.save(sport);
    }

    //Auth: admin
    public void updateSport(Integer id, Sport sport) {
        Sport oldSport = sportRepository.findSportById(id);
        if(oldSport ==null){
            throw new ApiException("Sport is not found");
        }
        oldSport.setName(sport.getName());
        sportRepository.save(oldSport);

    }

    //Auth: Admin
    public void deleteSport(Integer id) {
        Sport sport = sportRepository.findSportById(id);
        if(sport ==null){
            throw new ApiException("Sport is not found");
        }
        sportRepository.delete(sport);
    }

    //trainee auth
    public String getNutritionTip(Integer sportId) {
        Sport sport = sportRepository.findSportById(sportId);
        if (sport == null) throw new ApiException("Sport not found");

        String prompt = "Give me one specific, healthy snack recommendation to eat 30 minutes before a " + sport.getName() + " training session. Keep it under 20 words, answer in arabic.";
        return aiService.chat(prompt);
    }

    //trainee auth
    public String getHomeWorkout(Integer sportId) {
        Sport sport = sportRepository.findSportById(sportId);
        if (sport == null) throw new ApiException("Sport not found");

        String prompt = "Describe one simple " + sport.getName() + " workout I can do at home in 5 minutes without equipment, answer in arabic";
        return aiService.chat(prompt);
    }


}
