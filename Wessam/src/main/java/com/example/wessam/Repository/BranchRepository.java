package com.example.wessam.Repository;

import com.example.wessam.Model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch,Integer>
{
    Branch findBranchById(Integer id);   
    List<Branch> findBranchByGymId(Integer gymId);
}
