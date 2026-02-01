package com.launchmentor.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "qa_pairs")
public class QaPair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String question;

    @Column(columnDefinition = "TEXT")
    private String answer;

    // comma-separated keywords e.g. "resume,ats,keywords"
    @Column(length = 1000)
    private String keywords;

    // role or category (e.g. "Java Developer", "FullStack", or "General")
    @Column(length = 200)
    private String role = "General";

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public QaPair() {
    }

    public QaPair(String question, String answer, String keywords, String role) {
        this.question = question;
        this.answer = answer;
        this.keywords = keywords;
        this.role = role == null ? "General" : role;
        this.createdAt = LocalDateTime.now();
    }

    // --- getters & setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = (role == null || role.isBlank()) ? "General" : role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
