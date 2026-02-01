package com.launchmentor.backend.repository;

import com.launchmentor.backend.entity.Tip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipRepository extends JpaRepository<Tip, Long> {
    // No custom methods yet — basic CRUD comes from JpaRepository
}
