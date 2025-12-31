package com.example.wessam.Repository;

import com.example.wessam.Model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoachRepository extends JpaRepository<Coach,Integer> {
    Coach findCoachById(Integer id);
    List<Coach> findCoachByBranch_Gym_Id(Integer branchGymId);

}
