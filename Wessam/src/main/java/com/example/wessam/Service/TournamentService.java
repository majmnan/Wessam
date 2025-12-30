package com.example.wessam.Service;


import com.example.wessam.Api.ApiException;
import com.example.wessam.Model.Organizer;
import com.example.wessam.Model.Sport;
import com.example.wessam.Model.Tournament;
import com.example.wessam.Repository.OrganizerRepository;
import com.example.wessam.Repository.SportRepository;
import com.example.wessam.Repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentService {
    private final TournamentRepository tournamentRepository;
    private final OrganizerRepository organizerRepository;
    private final SportRepository sportRepository;
    private final AiService aiService;

    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    //organizerId will come from authenticated USER in controller @AuthenticationPrincipal
    public void addTournament(Integer organizerId, Tournament tournament, Integer sportId) {
        Organizer organizer = organizerRepository.findOrganizerById(organizerId);
        if (organizer == null)
            throw new ApiException("organizer not found");
        if(!organizer.getStatus().equalsIgnoreCase("Active"))
            throw new ApiException("organizer is not activated yet");
        Sport sport = sportRepository.findSportById(sportId);
        if (sport == null)
            throw new ApiException("sport not found");

        tournament.setOrganizer(organizer);
        tournament.setSport(sport);
        tournamentRepository.save(tournament);
    }

    public void updateTournament(Integer organizerId,Integer tournamentId,Integer sportId,Tournament tournament){
        Tournament oldTournament = tournamentRepository.findTournamentById(tournamentId);
        if(oldTournament==null)
            throw new ApiException("tournament Id not found");
        if (!oldTournament.getOrganizer().getId().equals(organizerId))
            throw new ApiException("You do not have permission to update this tournament");
        Sport sport = sportRepository.findSportById(sportId);
        if (sport == null)
            throw new ApiException("Sport not found");
        oldTournament.setName(tournament.getName());
        oldTournament.setReward(tournament.getReward());
        oldTournament.setStartDate(tournament.getStartDate());
        oldTournament.setEndDate(tournament.getEndDate());
        oldTournament.setSport(sport);
        tournamentRepository.save(oldTournament);
    }
    public void deleteTournament(Integer organizerId, Integer tournamentId) {
        Tournament tournament = tournamentRepository.findTournamentById(tournamentId);
        if (tournament == null) {
            throw new ApiException("tournament not found");
        }
        if (!tournament.getOrganizer().getId().equals(organizerId)) {
            throw new ApiException("you do not have permission to delete this tournament");
        }
        tournamentRepository.delete(tournament);
    }

    // trainee auth
    public String generateSocialPost(Integer tournamentId) {
        Tournament tournament = tournamentRepository.findTournamentById(tournamentId);
        if (tournament == null) throw new ApiException("Tournament not found");

        String prompt = "Write in arabic, an exciting Instagram caption with hashtags for a player who just joined the " + tournament.getName() + " tournament";
        return aiService.callAi(prompt);
    }

}
