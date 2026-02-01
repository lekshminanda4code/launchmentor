package com.launchmentor.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "careers")
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // e.g. "FullStack Developer"

    @Column(columnDefinition = "TEXT")
    private String description;

    // comma separated skills: "java,spring,angular"
    @Column(columnDefinition = "TEXT")
    private String skills;

    // roadmap or steps, plain text
    @Column(columnDefinition = "TEXT")
    private String roadmap;

    // optional resource link or path to pdf in /templates
    private String resourcesUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Career() {}

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public String getRoadmap() { return roadmap; }
    public void setRoadmap(String roadmap) { this.roadmap = roadmap; }

    public String getResourcesUrl() { return resourcesUrl; }
    public void setResourcesUrl(String resourcesUrl) { this.resourcesUrl = resourcesUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
