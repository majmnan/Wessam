package com.example.wessam.Service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ZoomService {

    private final ZoomJwtUtil zoomJwtUtil;
    private final RestTemplate restTemplate = new RestTemplate();

    public ZoomService(ZoomJwtUtil zoomJwtUtil) {
        this.zoomJwtUtil = zoomJwtUtil;
    }

    public String createMeeting(String topic, String startTime) {
        String token = zoomJwtUtil.generateToken();

        String url = "https://api.zoom.us/v2/users/me/meetings";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("topic", topic);
        body.put("type", 2); // scheduled meeting
        body.put("start_time", startTime); // format: yyyy-MM-dd'T'HH:mm:ss
        body.put("duration", 60); // in minutes

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        return response.getBody();
    }
}