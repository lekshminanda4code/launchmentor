package com.launchmentor.backend.service;

import com.launchmentor.backend.entity.QaPair;
import com.launchmentor.backend.repository.QaPairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatbotService {

    @Autowired
    private QaPairRepository qaPairRepository;

    private static final Set<String> STOPWORDS = Set.of(
            "the","is","and","a","an","how","to","for","in","on","of","i","my","you","me","what","which","when"
    );

    private List<String> tokenize(String text) {
        if (text == null) return Collections.emptyList();
        String clean = text.toLowerCase().trim();
        clean = clean.replaceAll("[^a-z0-9]+", " "); // remove punctuation
        return Arrays.stream(clean.split("\\s+"))
                .filter(s -> !s.isBlank() && !STOPWORDS.contains(s))
                .collect(Collectors.toList());
    }

    public String answerQuestion(String userQuestion, String role) {

        List<String> userTokens = tokenize(userQuestion);
        if (userTokens.isEmpty()) {
            return "Please ask a clear question about careers, resumes, or interviews.";
        }

        // Load QA pairs filtered by role or general
        List<QaPair> qaList = (role == null || role.isBlank())
                ? qaPairRepository.findAll()
                : qaPairRepository.findByRoleIgnoreCaseOrRoleIgnoreCase(role, "General");

        if (qaList.isEmpty()) {
            return "Sorry, I couldn't find any information for your question.";
        }

        QaPair bestQa = null;
        double bestScore = 0;

        for (QaPair qa : qaList) {
            List<String> keywords = Arrays.stream(
                            Optional.ofNullable(qa.getKeywords()).orElse("")
                                    .toLowerCase()
                                    .split("[,\\s]+"))
                    .filter(s -> !s.isBlank())
                    .collect(Collectors.toList());

            if (keywords.isEmpty()) continue;

            // Compute simple TF-like score: count overlap / total keywords
            long overlap = userTokens.stream()
                    .filter(keywords::contains)
                    .count();

            double score = (double) overlap / keywords.size();

            // Prefer exact role matches
            if (role != null && role.equalsIgnoreCase(qa.getRole())) {
                score += 0.1; // small boost
            }

            if (score > bestScore) {
                bestScore = score;
                bestQa = qa;
            }
        }

        if (bestQa != null && bestScore > 0) {
            return bestQa.getAnswer();
        }

        return "I am sorry, I don't have a direct answer to that. Try asking about resume, interview, communication, or specific technologies like Java or Angular.";
    }
}
