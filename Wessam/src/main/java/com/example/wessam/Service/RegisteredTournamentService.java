package com.example.wessam.Service;

import com.example.wessam.Api.ApiException;
import com.example.wessam.Model.RegisteredTournament;
import com.example.wessam.Model.Tournament;
import com.example.wessam.Model.Trainee;
import com.example.wessam.Repository.RegisteredTournamentRepository;
import com.example.wessam.Repository.TournamentRepository;
import com.example.wessam.Repository.TraineeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisteredTournamentService {
    private final RegisteredTournamentRepository registeredTournamentRepository;
    private final TraineeRepository traineeRepository;
    private final TournamentRepository tournamentRepository;

    public List<RegisteredTournament> getAllRegisteredTournaments() {
        return registeredTournamentRepository.findAll();
    }

    public void addRegisteredTournament(Integer traineeId, Integer tournamentId) {
        Trainee trainee = traineeRepository.findTraineeById(traineeId);
        if (trainee == null)
            throw new ApiException("trainee not found");
        Tournament tournament = tournamentRepository.findTournamentById(tournamentId);
        if (tournament == null)
            throw new ApiException("tournament not found");

        if (tournament.getStartDate().isBefore(LocalDate.now()))
            throw new ApiException("cannot register for a past tournament");

        RegisteredTournament registeredTournament = new RegisteredTournament(null,trainee,tournament);
        registeredTournamentRepository.save(registeredTournament);
    }

    public void updateRegisteredTournament(Integer traineeId, Integer oldTournamentRegId, Integer newTournamentId) {
        RegisteredTournament oldTournamentReg = registeredTournamentRepository.findRegisteredTournamentById(oldTournamentRegId);

        if (oldTournamentReg == null)
            throw new ApiException("registration not found");

        if (!oldTournamentReg.getTrainee().getId().equals(traineeId))
            throw new ApiException("you do not have permission to update this registration");

        Tournament newTournament = tournamentRepository.findTournamentById(newTournamentId);
        if (newTournament == null)
            throw new ApiException("new tournament not found");

        if (newTournament.getStartDate().isBefore(LocalDate.now()))
            throw new ApiException("cannot register for a past tournament");

        oldTournamentReg.setTournament(newTournament);
        registeredTournamentRepository.save(oldTournamentReg);
    }

    public void deleteRegisteredTournament(Integer traineeId, Integer id) {
        RegisteredTournament registeredTournament = registeredTournamentRepository.findRegisteredTournamentById(id);
        if (registeredTournament == null) {
            throw new ApiException("Registration not found");
        }
        if (!registeredTournament.getTrainee().getId().equals(traineeId)) {
            throw new ApiException("You do not have permission to cancel this registration");
        }
        registeredTournamentRepository.delete(registeredTournament);
    }

}
