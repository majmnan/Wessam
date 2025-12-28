package com.example.wessam.Service;

import com.example.wessam.DTO.OpenAI.OpenAiRecords;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class AiService {
    private final RestClient restClient;

    @Value("${openai.api.key}")
    private String apiKey;

    private final String API_URL = "https://api.openai.com/v1/chat/completions";

    public String chat(String prompt) {
        try {

            OpenAiRecords.AiRequest request = new OpenAiRecords.AiRequest("gpt-5", prompt);

            OpenAiRecords.AiResponse response = restClient.post()
                    .uri(API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + apiKey)
                    .body(request)
                    .retrieve()
                    .body(OpenAiRecords.AiResponse.class);

            if (response != null && !response.choices().isEmpty()) {
                return response.choices().get(0).message().content();
            }
            return "No response";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String toJson(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("JSON serialization failed", e);
        }
    }

    String extractJsonArray(String text) {

        text = text.replace("```json", "").replace("```", "").trim();

        int start = text.indexOf('[');
        int end = text.lastIndexOf(']');

        if (start == -1 || end == -1 || end <= start) {
            throw new RuntimeException("AI response does not contain a JSON array");
        }

        return text.substring(start, end + 1);
    }
}