package com.launchmentor.backend.controller;

import com.launchmentor.backend.model.InterviewQuestion;
import com.launchmentor.backend.service.InterviewQuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mock")
@CrossOrigin(origins = "*")
public class MockInterviewController {

    private final InterviewQuestionService interviewService;

    public MockInterviewController(InterviewQuestionService interviewService) {
        this.interviewService = interviewService;
    }

    // GET /api/mock/{role} - returns list of questions for that role
    @GetMapping("/{role}")
    public ResponseEntity<?> mockForRole(@PathVariable String role) {
        List<InterviewQuestion> list = interviewService.findByRole(role);
        if (list == null || list.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message","No questions found for role: " + role));
        }
        return ResponseEntity.ok(list);
    }
}

