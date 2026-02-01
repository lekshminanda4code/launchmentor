package com.launchmentor.backend.service;

import com.launchmentor.backend.entity.QaPair;
import com.launchmentor.backend.repository.QaPairRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QaPairService {

    private final QaPairRepository repo;

    public QaPairService(QaPairRepository repo) {
        this.repo = repo;
    }

    // Get all QA rows
    public List<QaPair> getAll() {
        return repo.findAll();
    }

    // Get by ID
    public Optional<QaPair> getById(Long id) {
        return repo.findById(id);
    }

    // Add new QA
    public QaPair save(QaPair qa) {
        return repo.save(qa);
    }

    // Update existing QA — must return Optional
    public Optional<QaPair> update(Long id, QaPair updated) {
        return repo.findById(id)
                .map(old -> {
                    old.setQuestion(updated.getQuestion());
                    old.setAnswer(updated.getAnswer());
                    old.setKeywords(updated.getKeywords());
                    old.setRole(updated.getRole());
                    return repo.save(old);
                });
    }

    // Delete — must return boolean
    public boolean delete(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
