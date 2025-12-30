package com.example.wessam.Service;

import com.example.wessam.Api.ApiException;
import com.example.wessam.Model.Sport;
import com.example.wessam.Repository.CourseRegistrationRepository;
import com.example.wessam.Repository.SportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SportService {

    private final CourseRegistrationRepository courseRegistrationRepository;
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

    public String sportAnalyze( Integer sportId) {
        Sport sport = sportRepository.findSportById(sportId);
        if(sport ==null){
            throw new ApiException("Sport is not found");
        }
        Integer taineeCount=courseRegistrationRepository.countTraineesBySport(sportId);
        Double ave=courseRegistrationRepository.avgRatingBySport(sportId);
        String prompt = "Analyze the popularity of the sport " + sport.getName() +
                " based on " + taineeCount + " registered trainees and an average rating of " +
                ave + ". " +
                "Provide a short analysis and advise gym owners whether investing in this sport would be beneficial.";

        return aiService.chat(prompt);
    }



}
