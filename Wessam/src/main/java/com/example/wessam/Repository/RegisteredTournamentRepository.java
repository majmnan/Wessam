package com.example.wessam.Repository;

import com.example.wessam.Model.RegisteredTournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisteredTournamentRepository extends JpaRepository<RegisteredTournament,Integer> {

    RegisteredTournament findRegisteredTournamentById(Integer id);

    @Query("select COUNT(DISTINCT tr.trainee.id)" +
            " from RegisteredTournament tr " +
            "where tr.tournament.id=:tournamentId")
    Integer TraineeRegisteredTournamentCount(@Param("tournamentId")Integer tournamentId);


}
