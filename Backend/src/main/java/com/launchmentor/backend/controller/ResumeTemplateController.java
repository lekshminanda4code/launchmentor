package com.launchmentor.backend.controller;

import com.launchmentor.backend.entity.ResumeTemplate;
import com.launchmentor.backend.service.ResumeTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;



@RestController
@RequestMapping("/api/resume-templates")
@CrossOrigin(origins = "*")
public class ResumeTemplateController {

    private final ResumeTemplateService service;

    public ResumeTemplateController(ResumeTemplateService service) {
        this.service = service;
    }

    // GET /api/resume-templates  -> list metadata (frontend will use fileUrl to download)
    @GetMapping
    public ResponseEntity<List<ResumeTemplate>> list() {
        List<ResumeTemplate> list = service.getAllTemplates();
        return ResponseEntity.ok(list);
    }

    // GET /api/resume-templates/{id} -> single metadata
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<ResumeTemplate> opt = service.getById(id);

        if (opt.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Template not found");
            return ResponseEntity.status(404).body(error);
        }

        // Found - return the ResumeTemplate object (200 OK)
        ResumeTemplate template = opt.get();
        return ResponseEntity.ok(template);
    }


    // POST /api/resume-templates -> add metadata
    // Body example: { "title":"Simple One-Page", "description":"...", "fileUrl":"/templates/simple_one_page.pdf" }
    @PostMapping
    public ResponseEntity<?> add(@RequestBody ResumeTemplate t) {
        if (t.getTitle() == null || t.getTitle().isBlank() || t.getFileUrl() == null || t.getFileUrl().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "title and fileUrl are required"));
        }
        ResumeTemplate saved = service.addTemplate(t);
        return ResponseEntity.ok(saved);
    }

    // DELETE /api/resume-templates/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteTemplate(id);
        return ResponseEntity.ok(Map.of("message","Template deleted"));
    }
}

