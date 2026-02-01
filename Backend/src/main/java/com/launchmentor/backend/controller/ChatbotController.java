package com.launchmentor.backend.controller;

import com.launchmentor.backend.service.ChatbotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    /**
     * POST /api/chat
     * Body: { "question": "...", "role": "Java Developer" }  // role optional
     */
    @PostMapping
    public ResponseEntity<?> chat(@RequestBody Map<String, String> payload) {
        String question = payload.get("question");
        String role = payload.get("role");

        if (question == null || question.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Please provide a question."));
        }

        String answer = chatbotService.answerQuestion(question, role);

        Map<String, String> resp = new HashMap<>();
        resp.put("question", question);
        resp.put("answer", answer);
        if (role != null && !role.isBlank()) resp.put("role", role);

        return ResponseEntity.ok(resp);
    }
}
