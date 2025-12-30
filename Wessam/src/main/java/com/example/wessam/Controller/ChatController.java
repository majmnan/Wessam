package com.example.wessam.Controller;

import com.example.wessam.Service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {

    private final AiService aiService;

    @PostMapping("/ask")
    public ResponseEntity<?> ask(@RequestBody String userMessage) {
        String aiResponse = aiService.callAi(userMessage);
        return ResponseEntity.status(200).body(aiResponse);
    }
}