package com.example.wessam;

import com.example.wessam.Api.ApiException;
import com.example.wessam.Model.Sport;
import com.example.wessam.Repository.SportRepository;
import com.example.wessam.Service.SportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SportServiceTest {

    @InjectMocks
    SportService sportService;

    @Mock
    SportRepository sportRepository;

    Sport sport;
    List<Sport> sports;

    @BeforeEach
    void setUp() {
        sport = new Sport(1, "Football", null, null, null);
        sports = new ArrayList<>();
        sports.add(sport);
    }

    @Test
    public void getSportsTest() {
        when(sportRepository.findAll()).thenReturn(sports);
        List<Sport> result = sportService.getSports();
        Assertions.assertEquals(1, result.size());
        verify(sportRepository, times(1)).findAll();
    }

    @Test
    public void addSportTest() {
        sportService.addSport(sport);
        verify(sportRepository, times(1)).save(sport);
    }

    // path is correct
    @Test
    public void updateSportTest() {
        when(sportRepository.findSportById(sport.getId())).thenReturn(sport);

        Sport updateInfo = new Sport(null, "Updated Name", null, null, null);
        sportService.updateSport(sport.getId(), updateInfo);

        verify(sportRepository, times(1)).save(sport);
        Assertions.assertEquals("Updated Name", sport.getName());
    }

    // path is not correct
    @Test
    public void updateSportNotFoundTest() {
        when(sportRepository.findSportById(sport.getId())).thenReturn(null);

        Assertions.assertThrows(ApiException.class, () -> {
            sportService.updateSport(sport.getId(), sport);
        });
    }

    @Test
    public void deleteSportTest() {
        when(sportRepository.findSportById(sport.getId())).thenReturn(sport);
        sportService.deleteSport(sport.getId());
        verify(sportRepository, times(1)).delete(sport);
    }
}