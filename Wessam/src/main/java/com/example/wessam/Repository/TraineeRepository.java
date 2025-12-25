package com.example.wessam.Repository;

import com.example.wessam.DTO.OUT.TraineeDTOOut;
import com.example.wessam.Model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee,Integer> {
    Trainee findTraineeById(Integer id);


}
