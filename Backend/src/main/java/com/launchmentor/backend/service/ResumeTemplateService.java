package com.launchmentor.backend.service;

import com.launchmentor.backend.entity.ResumeTemplate;
import com.launchmentor.backend.repository.ResumeTemplateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResumeTemplateService {

    private final ResumeTemplateRepository repo;

    public ResumeTemplateService(ResumeTemplateRepository repo) {
        this.repo = repo;
    }

    public List<ResumeTemplate> getAllTemplates() {
        return repo.findAll();
    }

    public Optional<ResumeTemplate> getById(Long id) {
        return repo.findById(id);
    }

    public ResumeTemplate addTemplate(ResumeTemplate t) {
        // you may validate title & fileUrl here
        return repo.save(t);
    }

    public void deleteTemplate(Long id) {
        repo.deleteById(id);
    }
}
