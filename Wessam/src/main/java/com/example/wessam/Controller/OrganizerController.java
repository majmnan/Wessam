package com.example.wessam.Controller;

import com.example.wessam.Service.OrganizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/organizer")
public class OrganizerController {

    private final OrganizerService organizerService;
}
