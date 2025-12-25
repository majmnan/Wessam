package com.example.wessam.Repository;

import com.example.wessam.Model.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportRepository extends JpaRepository<Sport,Integer> {
    Sport findSportById(Integer id);
}
