package com.example.wessam.Service;

import com.example.wessam.DTO.OUT.BranchDTOOut;
import com.example.wessam.Model.Branch;
import com.example.wessam.Repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchService {

    private ModelMapper mapper;

    private final BranchRepository branchRepository;

    //Auth: Gym
    public List<BranchDTOOut> getByGym(Integer gymId){
        return branchRepository.findBranchByGymId(gymId)
                .stream().map(b->mapper.map(b, BranchDTOOut.class)).toList();
    }
}
