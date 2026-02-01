package com.launchmentor.backend.repository;

import com.launchmentor.backend.model.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion, Long> {

    // existing method → keep it
    List<InterviewQuestion> findByRoleIgnoreCase(String role);

    // new method → add this
    Optional<InterviewQuestion> findFirstByRoleIgnoreCaseAndQuestionIgnoreCase(String role, String question);
}
