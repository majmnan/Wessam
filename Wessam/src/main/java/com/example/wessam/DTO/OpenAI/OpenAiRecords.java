package com.example.wessam.DTO.OpenAI;

import java.util.List;

public record OpenAiRecords() {
    public record AiRequest(String model, List<Message> messages, Double temperature) {
        public AiRequest(String model, String prompt) {
            this(model, List.of(new Message("user", prompt)), 0.0);
        }
    }
    public record Message(String role, String content) {}
    public record AiResponse(List<Choice> choices) {}
    public record Choice(Message message) {}
}
