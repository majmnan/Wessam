package com.example.wessam.Controller;

import com.example.wessam.Api.ApiResponse;
import com.example.wessam.DTO.IN.GymDTOIn;
import com.example.wessam.DTO.IN.OrganizerDTOIn;
import com.example.wessam.Model.User;
import com.example.wessam.Service.OrganizerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/organizer")
public class OrganizerController {

    private final OrganizerService organizerService;



    @GetMapping("/get")
    public ResponseEntity<?> getOrganizers() {
        return ResponseEntity.ok(organizerService.getOrganizers());
    }


    @PostMapping("/add")
    public ResponseEntity<?> addOrganizer(@RequestBody @Valid OrganizerDTOIn organizerDTOIn) {
        organizerService.addOrganizer(organizerDTOIn);
        return ResponseEntity.ok(new ApiResponse("Organizer is added successfully"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOrganizer(@PathVariable Integer id, @AuthenticationPrincipal User user, @RequestBody @Valid OrganizerDTOIn organizerDTOIn) {
        organizerService.updateOrganizer(id, organizerDTOIn);
        return ResponseEntity.ok(new ApiResponse("Organizer is updated successfully"));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrganizer(@PathVariable Integer id, @AuthenticationPrincipal User user) {
        organizerService.deleteOrganizer(id);
        return ResponseEntity.ok(new ApiResponse("Organizer is deleted successfully"));
    }

}
