package com.example.wessam;

import com.example.wessam.Api.ApiException;
import com.example.wessam.DTO.IN.GymDTOIn;
import com.example.wessam.DTO.OUT.GymDTOOut;
import com.example.wessam.Model.Gym;
import com.example.wessam.Model.User;
import com.example.wessam.Repository.AuthRepository;
import com.example.wessam.Repository.GymRepository;
import com.example.wessam.Service.GymService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GymServiceTest {

    @InjectMocks
    GymService gymService;

    @Mock
    GymRepository gymRepository;
    @Mock
    AuthRepository authRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    Gym gym;
    User user;
    List<Gym> gyms;

    @BeforeEach
    void setUp() {
        user = new User(1, "gymUser", "pass", "GYM", null, null, null, null);

        gym = new Gym(1, "Iron Gym", "12345", "Active",LocalDate.of(2026,10,10),"Desc", null, user);
        gyms = new ArrayList<>();
        gyms.add(gym);
    }

    @Test
    public void getAllGymsTest() {
        when(gymRepository.findAll()).thenReturn(gyms);
        List<GymDTOOut> result = gymService.getAllGyms();
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Iron Gym", result.get(0).getName());
        verify(gymRepository, times(1)).findAll();
    }

    @Test
    public void registerGymTest() {
        GymDTOIn dto = new GymDTOIn("username", "pass", "Name", "123456", "Desc");

        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPass");
        when(authRepository.save(any(User.class))).thenReturn(user);

        gymService.register(dto);

        verify(authRepository, times(1)).save(any(User.class));
        verify(gymRepository, times(1)).save(any(Gym.class));
    }

    @Test
    public void activateGymTest() {
        gym.setStatus("InActive");
        when(gymRepository.findGymById(gym.getId())).thenReturn(gym);

        gymService.activateGym(gym.getId());

        Assertions.assertEquals("Active", gym.getStatus());
        verify(gymRepository, times(1)).save(gym);
    }

    @Test
    public void deactivateGymTest() {
        // gym is Active in setUp
        when(gymRepository.findGymById(gym.getId())).thenReturn(gym);

        gymService.deactivateGym(gym.getId());

        Assertions.assertEquals("InActive", gym.getStatus());
        verify(gymRepository, times(1)).save(gym);
    }

    @Test
    public void activateGymAlreadyActiveTest() {
        // gym is Active in setUp
        when(gymRepository.findGymById(gym.getId())).thenReturn(gym);

        Assertions.assertThrows(ApiException.class, () -> {
            gymService.activateGym(gym.getId());
        });
    }
}