package com.launchmentor.backend.service;

import com.launchmentor.backend.entity.Career;
import com.launchmentor.backend.repository.CareerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CareerService {

    private final CareerRepository repo;

    public CareerService(CareerRepository repo) {
        this.repo = repo;
    }

    public List<Career> getAll() {
        return repo.findAll();
    }

    public Optional<Career> getById(Long id) {
        return repo.findById(id);
    }

    public Career add(Career c) {
        return repo.save(c);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
    
    public Career save(Career career) {
        return repo.save(career);
    }


    public List<Career> search(String q) {
        if (q == null || q.isBlank()) return repo.findAll();
        return repo.findByNameContainingIgnoreCase(q);
    }
}
