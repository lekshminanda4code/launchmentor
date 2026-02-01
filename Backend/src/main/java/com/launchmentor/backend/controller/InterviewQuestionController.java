package com.launchmentor.backend.controller;

import com.launchmentor.backend.model.InterviewQuestion;
import com.launchmentor.backend.service.InterviewQuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/interview-questions")
@CrossOrigin(origins = "*")
public class InterviewQuestionController {

    private final InterviewQuestionService service;

    public InterviewQuestionController(InterviewQuestionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<InterviewQuestion>> list() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<InterviewQuestion>> byRole(@PathVariable String role) {
        return ResponseEntity.ok(service.findByRole(role));
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody InterviewQuestion q) {
        if (q.getRole() == null || q.getRole().isBlank() || q.getQuestion() == null || q.getQuestion().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "role and question required"));
        }
        return ResponseEntity.ok(service.add(q));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(Map.of("message", "Deleted"));
    }

    // Mock interview endpoint
    // e.g. GET /api/interview-questions/mock?role=Java Developer&count=5
    @GetMapping("/mock")
    public ResponseEntity<List<InterviewQuestion>> mock(@RequestParam(name = "role", required = false) String role,
                                                        @RequestParam(name = "count", required = false, defaultValue = "5") int count) {
        return ResponseEntity.ok(service.getMockQuestions(role, count));
    }
}
