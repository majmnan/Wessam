package com.example.wessam;

import com.example.wessam.Model.Sport;
import com.example.wessam.Repository.SportRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SportRepositoryTest {

    @Autowired
    SportRepository sportRepository;

    Sport sport;

    @BeforeEach
    void setUp() {
        sport = new Sport(null, "Football", null, null, null);
    }

    @Test
    public void findSportByIdTest() {
        sportRepository.save(sport);
        Sport found = sportRepository.findSportById(sport.getId());
        Assertions.assertThat(found).isEqualTo(sport);
    }

    @Test
    public void findAllSportsTest() {
        Sport sport2 = new Sport(null, "Tennis", null, null, null);
        sportRepository.save(sport);
        sportRepository.save(sport2);

        List<Sport> sports = sportRepository.findAll();
        Assertions.assertThat(sports.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void saveSportTest() {
        Sport savedSport = sportRepository.save(sport);
        Assertions.assertThat(savedSport).isNotNull();
        Assertions.assertThat(savedSport.getId()).isNotNull();
    }

    @Test
    public void updateSportTest() {
        sportRepository.save(sport);
        sport.setName("Basketball");
        Sport updatedSport = sportRepository.save(sport);
        Assertions.assertThat(updatedSport.getName()).isEqualTo("Basketball");
    }

    @Test
    public void deleteSportTest() {
        sportRepository.save(sport);
        sportRepository.delete(sport);
        Sport found = sportRepository.findSportById(sport.getId());
        Assertions.assertThat(found).isNull();
    }
}