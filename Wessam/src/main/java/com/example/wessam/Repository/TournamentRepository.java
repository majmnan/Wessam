package com.example.wessam.Repository;

import com.example.wessam.Model.Course;
import com.example.wessam.Model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament,Integer> {
    Tournament findTournamentById(Integer id);

    @Query("select t from Tournament t where t.startDate>:startDate ")
    List<Tournament> findAllTournamentByStartDate(@Param("startDate")LocalDate startDate);


    @Query("select t from Tournament t where t.sport.id = :sportId and t.startDate between :startDate and :endDate")
    List<Tournament> findTournamentByDate(@Param("sportId")Integer id, @Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);
}
