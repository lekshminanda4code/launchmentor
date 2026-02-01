package com.launchmentor.backend.service;

import com.launchmentor.backend.model.InterviewQuestion;
import com.launchmentor.backend.repository.InterviewQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MockInterviewService {

    @Autowired
    private InterviewQuestionRepository repo;

    public List<InterviewQuestion> getRandomQuestions(String role, int count) {

        List<InterviewQuestion> questions =
                (role == null || role.isBlank())
                        ? repo.findAll()
                        : repo.findByRoleIgnoreCase(role);

        if (questions.isEmpty()) return Collections.emptyList();

        Collections.shuffle(questions);

        return questions.stream()
                .limit(count)
                .collect(Collectors.toList());
    }
}
