package com.launchmentor.backend.repository;

import com.launchmentor.backend.entity.ResumeTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeTemplateRepository extends JpaRepository<ResumeTemplate, Long> {
    // default JpaRepository methods are enough: findAll(), findById(), save(), deleteById()
}
