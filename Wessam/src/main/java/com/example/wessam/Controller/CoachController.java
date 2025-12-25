package com.example.wessam.Controller;

import com.example.wessam.Service.CoachService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coach")
public class CoachController {

    private final CoachService coachService;
}
