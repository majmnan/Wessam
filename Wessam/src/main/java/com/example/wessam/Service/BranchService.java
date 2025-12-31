package com.example.wessam.Service;

import com.example.wessam.Api.ApiException;
import com.example.wessam.DTO.IN.BranchDTOIn;
import com.example.wessam.DTO.OUT.BranchDTOOut;
import com.example.wessam.Model.Branch;
import com.example.wessam.Model.Gym;
import com.example.wessam.Repository.BranchRepository;
import com.example.wessam.Repository.GymRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final ModelMapper mapper;
    private final BranchRepository branchRepository;
    private final GymRepository gymRepository;

    //Auth: Admin
    public List<BranchDTOOut> getAll(){
        return branchRepository.findAll().stream().map(b ->
                mapper.map(b,BranchDTOOut.class)
        ).toList();
    }

    //Auth: Gym
    public List<BranchDTOOut> getByGym(Integer gymId){
        Gym gym = gymRepository.findGymById(gymId);
        if(gym == null)
            throw new ApiException("gym not found");
        return branchRepository.findBranchByGymId(gymId)
                .stream().map(b->{
                    BranchDTOOut dto = mapper.map(b, BranchDTOOut.class);
                    dto.setName(gym.getName());
                    dto.setDescription(gym.getDescription());
                    return dto;
                }).toList();
    }

    //Auth: Gym
    public void addBranch(Integer gymId, BranchDTOIn dto){
        Gym gym =  gymRepository.findGymById(gymId);
        if(gym == null)
            throw new ApiException("gym not found");
        if(!gym.getStatus().equals("Active"))
            throw new ApiException("gym is inactive yet");
        Branch branch = mapper.map(dto,Branch.class);
        branch.setGym(gymRepository.findGymById(gymId));
        branchRepository.save(branch);
    }

    //Auth: gym
    public void updateBranch(Integer gymId, Integer branchId, BranchDTOIn dto) {
        Gym gym =  gymRepository.findGymById(gymId);
        if(gym == null)
            throw new ApiException("gym not found");
        if(!gym.getStatus().equals("Active"))
            throw new ApiException("gym is inactive yet");
        Branch branch = branchRepository.findBranchById(branchId);
        if(branch == null)
            throw new ApiException("Branch not found");

        if (!branch.getGym().getId().equals(gymId)) {
            throw new ApiException("Unauthorized access");
        }

        branch.setCity(dto.getCity());
        branch.setLocation(dto.getLocation());

        branchRepository.save(branch);
    }

    //Auth: gym
    public void deleteBranch(Integer gymId, Integer branchId) {
        Gym gym =  gymRepository.findGymById(gymId);
        if(gym == null)
            throw new ApiException("gym not found");
        if(!gym.getStatus().equals("Active"))
            throw new ApiException("gym is inactive yet");
        Branch branch = branchRepository.findBranchById(branchId);
        if(branch == null)
            throw new ApiException("Branch not found");

        if (!branch.getGym().getId().equals(gymId)) {
            throw new ApiException("Unauthorized access to this branch");
        }

        branchRepository.delete(branch);
    }

}
