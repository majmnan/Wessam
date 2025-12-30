package com.example.wessam.Controller;

import com.example.wessam.Service.ZoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/zoom")
public class ZoomController {

    private final ZoomService zoomService;

    public ZoomController(ZoomService zoomService) {
        this.zoomService = zoomService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createMeeting(@RequestParam String topic, @RequestParam String startTime) {
        String result = zoomService.createMeeting(topic, startTime);
        return ResponseEntity.ok(result);
    }
}