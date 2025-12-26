package com.example.wessam.Repository;

import com.example.wessam.Model.RegisteredTournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisteredTournamentRepository extends JpaRepository<RegisteredTournament,Integer> {

    RegisteredTournament findRegisteredTournamentById(Integer id);
}
