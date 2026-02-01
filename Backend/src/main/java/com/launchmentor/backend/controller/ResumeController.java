package com.launchmentor.backend.controller;

import com.launchmentor.backend.dto.ResumeCheckRequest;
import com.launchmentor.backend.service.CareerService;
import com.launchmentor.backend.service.ResumeCheckerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/resume")
@CrossOrigin(origins = "*")
public class ResumeController {

    private final ResumeCheckerService resumeCheckerService;
    private final CareerService careerService;

    public ResumeController(ResumeCheckerService resumeCheckerService, CareerService careerService) {
        this.resumeCheckerService = resumeCheckerService;
        this.careerService = careerService;
    }

    /**
     * POST /api/resume/check
     * Body: { "resumeText": "...", "jobDescription": "...", "careerId": 2 }
     * careerId optional — if present we use the career's skills as the job description.
     */
    @PostMapping("/check")
    public ResponseEntity<?> checkResume(@RequestBody ResumeCheckRequest req) {
        if (req == null || req.getResumeText() == null || req.getResumeText().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "resumeText is required"));
        }

        String jobDesc = req.getJobDescription();

        if (req.getCareerId() != null) {
            var opt = careerService.getById(req.getCareerId());
            if (opt.isEmpty()) {
                return ResponseEntity.status(404).body(Map.of("error", "Career not found"));
            }
            // use career skills as the job description (comma list)
            jobDesc = opt.get().getSkills();
        }

        var result = resumeCheckerService.checkResume(req.getResumeText(), jobDesc);
        return ResponseEntity.ok(result);
    }
}
