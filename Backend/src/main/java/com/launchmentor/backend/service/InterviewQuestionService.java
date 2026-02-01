package com.launchmentor.backend.service;

import com.launchmentor.backend.model.InterviewQuestion;
import com.launchmentor.backend.repository.InterviewQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterviewQuestionService {

    private final InterviewQuestionRepository repo;

    public InterviewQuestionService(InterviewQuestionRepository repo) {
        this.repo = repo;
    }

    public List<InterviewQuestion> getAll() {
        return repo.findAll();
    }

    public List<InterviewQuestion> findByRole(String role) {
        return repo.findByRoleIgnoreCase(role);
    }

    public InterviewQuestion add(InterviewQuestion q) {
        return repo.save(q);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<InterviewQuestion> getMockQuestions(String role, int count) {
        List<InterviewQuestion> list = (role == null || role.isBlank())
                ? repo.findAll()
                : repo.findByRoleIgnoreCase(role);

        if (list.isEmpty()) return Collections.emptyList();

        Collections.shuffle(list);
        return list.stream().limit(Math.max(0, count)).collect(Collectors.toList());
    }
}
