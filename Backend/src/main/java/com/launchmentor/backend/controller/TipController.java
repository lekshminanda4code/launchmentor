package com.launchmentor.backend.controller;

import com.launchmentor.backend.entity.Tip;
import com.launchmentor.backend.service.TipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tips")
@CrossOrigin(origins = "*")
public class TipController {

    @Autowired
    private TipService tipService;

    // Add new tip
    @PostMapping
    public ResponseEntity<Tip> addTip(@RequestBody Tip tip) {
        Tip saved = tipService.addTip(tip);
        return ResponseEntity.ok(saved);
    }

    // Get all tips
    @GetMapping
    public List<Tip> getAllTips() {
        return tipService.getAllTips();
    }

    // Get tip by id
    @GetMapping("/{id}")
    public ResponseEntity<Tip> getTipById(@PathVariable Long id) {
        Tip tip = tipService.getTipById(id);
        return ResponseEntity.ok(tip);
    }

    // Delete tip
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTip(@PathVariable Long id) {
        tipService.deleteTip(id);
        return ResponseEntity.ok("Tip deleted successfully");
    }
}
