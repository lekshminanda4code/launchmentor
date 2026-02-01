package com.launchmentor.backend.service;

import com.launchmentor.backend.entity.Tip;
import com.launchmentor.backend.repository.TipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipService {

    @Autowired
    private TipRepository tipRepository;

    // Create or Add new tip
    public Tip addTip(Tip tip) {
        return tipRepository.save(tip);
    }

    // Get all tips
    public List<Tip> getAllTips() {
        return tipRepository.findAll();
    }

    // Get one tip by ID
    public Tip getTipById(Long id) {
        return tipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tip not found"));
    }

    // Delete tip
    public void deleteTip(Long id) {
        tipRepository.deleteById(id);
    }
}
