package com.example.wessam.Service;

import com.example.wessam.Api.ApiException;
import com.example.wessam.DTO.IN.CoachDTOIn;
import com.example.wessam.DTO.IN.OrganizerDTOIn;
import com.example.wessam.DTO.OUT.CoachDTOOut;
import com.example.wessam.DTO.OUT.OrganizerDTOOut;
import com.example.wessam.Model.Coach;
import com.example.wessam.Model.Organizer;
import com.example.wessam.Model.User;
import com.example.wessam.Repository.AuthRepository;
import com.example.wessam.Repository.OrganizerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizerService {

    private final OrganizerRepository organizerRepository;
    private final AuthRepository authRepository;

    public List<OrganizerDTOOut> getOrganizers(){
        List<Organizer> organizers=organizerRepository.findAll();
        List<OrganizerDTOOut> organizerDTOOuts=new ArrayList<>();
        for(Organizer o: organizers){
            String username = o.getUser() != null ? o.getUser().getUsername() : null;
            organizerDTOOuts.add(new OrganizerDTOOut(username,
                    o.getName(),
                    o.getBusinessCertificateId(),
                    o.getStatus()
                    ));

        }
        return organizerDTOOuts;
    }


    public void addOrganizer(OrganizerDTOIn organizerDTOIn){
        Organizer organizer=new Organizer();
        String hashed=new BCryptPasswordEncoder().encode(organizerDTOIn.getPassword());
        User user=new User();
        user.setUsername(organizerDTOIn.getUsername());
        user.setPassword(hashed);
        user.setRole("ORGANIZER");
        organizer.setUser(user);
        organizer.setBusinessCertificateId(organizerDTOIn.getBusinessCertificateId());
        organizer.setName(organizerDTOIn.getName());
        organizer.setStatus(organizerDTOIn.getStatus());
        organizerRepository.save(organizer);
    }


    public void updateOrganizer(Integer id, OrganizerDTOIn organizerDTOIn) {
        Organizer organizer =organizerRepository.findOrganizerById(id);
        if(organizer ==null){
            throw new ApiException("Organizer is not found");
        }
        String hashed=new BCryptPasswordEncoder().encode(organizerDTOIn.getPassword());

        organizer.setBusinessCertificateId(organizerDTOIn.getBusinessCertificateId());
        organizer.setName(organizerDTOIn.getName());
        organizer.setStatus(organizerDTOIn.getStatus());
        User user=organizer.getUser();
        user.setUsername(organizerDTOIn.getUsername());
        user.setPassword(hashed);
        authRepository.save(user);
        organizerRepository.save(organizer);
    }

    public void deleteOrganizer(Integer id){
        Organizer organizer =organizerRepository.findOrganizerById(id);
        if(organizer ==null){
            throw new ApiException("Organizer is not found");
        }
        organizerRepository.delete(organizer);
    }
}
