package com.example.wessam.Service;

import com.example.wessam.Api.ApiException;
import com.example.wessam.DTO.IN.BranchDTOIn;
import com.example.wessam.DTO.OUT.BranchDTOOut;
import com.example.wessam.Model.Branch;
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
        return branchRepository.findBranchByGymId(gymId)
                .stream().map(b->mapper.map(b, BranchDTOOut.class)).toList();
    }

    //Auth: Gym
    public void addBranch(Integer gymId, BranchDTOIn dto){
        Branch branch = mapper.map(dto,Branch.class);
        branch.setGym(gymRepository.findGymById(gymId));
        branchRepository.save(branch);
    }

    //Auth: gym
    public void updateBranch(Integer gymId, Integer branchId, BranchDTOIn dto) {

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

        Branch branch = branchRepository.findBranchById(branchId);
        if(branch == null)
            throw new ApiException("Branch not found");

        if (!branch.getGym().getId().equals(gymId)) {
            throw new ApiException("Unauthorized access to this branch");
        }

        branchRepository.delete(branch);
    }

}
