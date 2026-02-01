package com.launchmentor.backend.controller;

import com.launchmentor.backend.entity.Career;
import com.launchmentor.backend.service.CareerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/careers")
@CrossOrigin(origins = "*")
public class CareerController {

    private final CareerService service;

    public CareerController(CareerService service) {
        this.service = service;
    }

    // ✅ GET all careers (with optional search)
    @GetMapping
    public ResponseEntity<List<Career>> list(
            @RequestParam(name = "q", required = false) String q) {
        return ResponseEntity.ok(service.search(q));
    }

    // ✅ GET career by id
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        var opt = service.getById(id);

        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        }

        Map<String, String> e = new HashMap<>();
        e.put("error", "Career not found");
        return ResponseEntity.status(404).body(e);
    }

    // ✅ POST add career
    @PostMapping
    public ResponseEntity<?> add(@RequestBody Career career) {
        if (career.getName() == null || career.getName().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "name required"));
        }
        Career saved = service.add(career);
        return ResponseEntity.ok(saved);
    }

    // ✅ DELETE career
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(Map.of("message", "Deleted"));
    }

    // ✅ PUT update career (FIXED)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCareer(
            @PathVariable Long id,
            @RequestBody Career updatedCareer) {

        return service.getById(id)   // ✅ FIXED METHOD NAME
                .map(existing -> {
                    existing.setName(updatedCareer.getName());
                    existing.setDescription(updatedCareer.getDescription());
                    existing.setSkills(updatedCareer.getSkills());
                    existing.setRoadmap(updatedCareer.getRoadmap());
                    existing.setResourcesUrl(updatedCareer.getResourcesUrl());

                    Career saved = service.save(existing); // ✅ FIXED VARIABLE
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}


