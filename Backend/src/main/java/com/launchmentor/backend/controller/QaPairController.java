package com.launchmentor.backend.controller;

import com.launchmentor.backend.entity.QaPair;
import com.launchmentor.backend.service.QaPairService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/qa")
@CrossOrigin(origins = "*")
public class QaPairController {

    @Autowired
    private QaPairService qaService;

    // GET ALL
    @GetMapping
    public List<QaPair> getAll() {
        return qaService.getAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<QaPair> qa = qaService.getById(id);

        if (qa.isPresent()) {
            return ResponseEntity.ok(qa.get());
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "QA Pair not found"));
        }
    }

    // ADD NEW
    @PostMapping
    public ResponseEntity<?> add(@RequestBody QaPair qa) {
        QaPair saved = qaService.save(qa);
        return ResponseEntity.ok(saved);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody QaPair updated) {

        Optional<QaPair> result = qaService.update(id, updated);

        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "QA Pair not found"));
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        boolean deleted = qaService.delete(id);

        if (deleted) {
            return ResponseEntity.ok(Map.of("message", "Deleted successfully"));
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "QA Pair not found"));
        }
    }
}
