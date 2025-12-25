package com.example.wessam.Controller;

import com.example.wessam.Service.GymService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gym")
public class GymController {

    private final GymService gymService;
}
