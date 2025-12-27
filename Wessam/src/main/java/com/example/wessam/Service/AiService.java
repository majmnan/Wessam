package com.example.wessam.Service;

import com.example.wessam.DTO.OpenAI.OpenAiRecords;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

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
}