package com.example.wessam.Controller;

import com.example.wessam.Model.RegisteredTournament;
import com.example.wessam.Service.RegisteredTournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/registeredTournament")
public class RegisteredTournamentController {

    private final RegisteredTournamentService registeredTournamentService;
}
