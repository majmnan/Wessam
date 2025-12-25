package com.example.wessam.Repository;

import com.example.wessam.Model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament,Integer> {
    Tournament findTournamentById(Integer id);

}
