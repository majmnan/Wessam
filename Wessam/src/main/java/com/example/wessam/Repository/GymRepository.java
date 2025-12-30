package com.example.wessam.Repository;

import com.example.wessam.Model.Gym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GymRepository extends JpaRepository<Gym,Integer> {
    Gym findGymById(Integer id);
    List<Gym> findAllByStatus(String status);
}
