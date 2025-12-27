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

    public List<Sport> getSports() {
        return sportRepository.findAll();
    }


    public void addSport(Sport sport) {
        sportRepository.save(sport);
    }


    public void updateSport(Integer id, Sport sport) {
        Sport oldSport = sportRepository.findSportById(id);
        if(oldSport ==null){
            throw new ApiException("Sport is not found");
        }
        oldSport.setName(sport.getName());
        sportRepository.save(oldSport);

    }

    public void deleteSport(Integer id) {
        Sport sport = sportRepository.findSportById(id);
        if(sport ==null){
            throw new ApiException("Sport is not found");
        }
        sportRepository.delete(sport);
    }


}
