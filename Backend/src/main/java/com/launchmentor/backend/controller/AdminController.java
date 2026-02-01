package com.launchmentor.backend.controller;

import com.launchmentor.backend.entity.Career;
import com.launchmentor.backend.entity.QaPair;
import com.launchmentor.backend.entity.ResumeTemplate;
import com.launchmentor.backend.model.InterviewQuestion;
import com.launchmentor.backend.service.CareerService;
import com.launchmentor.backend.service.ResumeTemplateService;
import com.launchmentor.backend.service.InterviewQuestionService;
import com.launchmentor.backend.service.QaPairService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final CareerService careerService;
    private final InterviewQuestionService interviewService;
    private final ResumeTemplateService resumeTemplateService;
    private final QaPairService qaPairService;

    public AdminController(CareerService careerService,
                           InterviewQuestionService interviewService,
                           ResumeTemplateService resumeTemplateService,
                           QaPairService qaPairService) {
        this.careerService = careerService;
        this.interviewService = interviewService;
        this.resumeTemplateService = resumeTemplateService;
        this.qaPairService = qaPairService;
    }

    // ========== Careers ==========
    @PostMapping("/careers")
    public ResponseEntity<?> addCareer(@RequestBody Career c) {
        if (c.getName() == null || c.getName().isBlank()) {
            return ResponseEntity.badRequest().body("name required");
        }
        // Use the service method that creates a career.
        // Many service implementations call this method "add" or "save".
        // Here we call add(...) as used earlier in the project.
        return ResponseEntity.ok(careerService.add(c));
    }

    @DeleteMapping("/careers/{id}")
    public ResponseEntity<?> deleteCareer(@PathVariable Long id) {
        careerService.delete(id);
        return ResponseEntity.ok("Deleted");
    }

    // ========== Interview questions ==========
    @PostMapping("/interview-questions")
    public ResponseEntity<?> addInterview(@RequestBody InterviewQuestion q) {
        return ResponseEntity.ok(interviewService.add(q));
    }

    // ========== QA pairs ==========
    @PostMapping("/qa")
    public ResponseEntity<?> addQa(@RequestBody QaPair qa) {
        // our QaPairService earlier used a method named save(...) so call that
        return ResponseEntity.ok(qaPairService.save(qa));
    }

    // ========== Resume templates ==========
 // ========== Resume templates ==========
    @PostMapping("/templates")
    public ResponseEntity<?> addTemplate(@RequestBody ResumeTemplate t) {
        if (t.getTitle() == null || t.getTitle().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error","title is required"));
        }
        if (t.getFileUrl() == null || t.getFileUrl().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error","fileUrl is required"));
        }
        ResumeTemplate saved = resumeTemplateService.addTemplate(t);
        return ResponseEntity.ok(saved);
    }

}
