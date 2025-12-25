package com.example.wessam.Controller;

import com.example.wessam.Service.SportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sport")
public class SportController {

    private final SportService sportService;


}
