package com.launchmentor.backend.repository;

import com.launchmentor.backend.entity.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {
    List<Career> findByNameContainingIgnoreCase(String q);
}
