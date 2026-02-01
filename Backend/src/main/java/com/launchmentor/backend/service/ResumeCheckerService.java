package com.launchmentor.backend.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ResumeCheckerService {

    // STOPWORDS for general token cleaning
    private static final Set<String> STOPWORDS = Set.of(
            "a","an","the","and","or","in","on","for","to","with","of","is","are","be","by","your","you","will","that","this",
            "looking","seeking","experience","candidate","applicant"
    );

    // SKILLS WHITELIST (expand as needed)
    private static final Set<String> SKILLS_SET = Set.of(
            "java","spring","spring boot","springboot","angular","react","rest","rest api","restapi","sql","mysql","git",
            "docker","kubernetes","html","css","javascript","node","python","c++","c","c#","aws","azure","linux",
            "mongodb","nosql","hibernate","jpa","spring mvc","microservices","gitlab","github","ci/cd","opencv"
    );

    // SYNONYMS / normalizations: variant -> canonical
    private static final Map<String,String> SYNONYMS = createSynonymMap();

    private static Map<String,String> createSynonymMap() {
        Map<String,String> m = new HashMap<>();
        m.put("springboot", "spring boot");
        m.put("spring-boot", "spring boot");
        m.put("restapi", "rest");
        m.put("rest-api", "rest");
        m.put("rest api", "rest");
        m.put("mysql", "sql");
        m.put("postgresql", "sql");
        m.put("postgres", "sql");
        m.put("gitlab", "git");
        m.put("github", "git");
        return m;
    }

    private static String normalizeToken(String t) {
        if (t == null) return null;
        t = t.trim().toLowerCase();
        if (SYNONYMS.containsKey(t)) return SYNONYMS.get(t);
        return t;
    }

    // Tokenization for resume: general tokens
    private Set<String> extractKeywordsFromResume(String text) {
        if (text == null) return Collections.emptySet();
        String clean = text.toLowerCase();
        clean = Pattern.compile("[^a-z0-9]+").matcher(clean).replaceAll(" ");
        String[] parts = clean.split("\\s+");
        return Arrays.stream(parts)
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .filter(s -> s.length() > 1)
                .filter(s -> !STOPWORDS.contains(s))
                .map(ResumeCheckerService::normalizeToken)
                .collect(Collectors.toSet());
    }

    // Extract skills from job description (whitelist + bigrams + synonyms)
    private Set<String> extractSkillsFromJob(String jobDescription) {
        if (jobDescription == null) return Collections.emptySet();
        String clean = jobDescription.toLowerCase();
        clean = Pattern.compile("[^a-z0-9]+").matcher(clean).replaceAll(" ");
        String[] parts = clean.split("\\s+");
        List<String> tokens = Arrays.stream(parts)
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .filter(s -> !STOPWORDS.contains(s))
                .collect(Collectors.toList());

        Set<String> found = new LinkedHashSet<>();

        // single tokens
        for (String tok : tokens) {
            String norm = normalizeToken(tok);
            if (norm == null || norm.isBlank()) continue;
            if (SKILLS_SET.contains(norm)) {
                // map to canonical form if synonyms point to canonical skill
                String canonical = SYNONYMS.getOrDefault(norm, norm);
                if (SKILLS_SET.contains(canonical)) found.add(canonical);
                else found.add(norm);
            }
        }

        // bigrams for multi-word skills like "spring boot"
        for (int i = 0; i < tokens.size() - 1; i++) {
            String bigramRaw = tokens.get(i) + " " + tokens.get(i + 1);
            String normBigram = normalizeToken(bigramRaw);
            if (normBigram == null || normBigram.isBlank()) normBigram = bigramRaw;
            if (SKILLS_SET.contains(normBigram)) {
                String canonical = SYNONYMS.getOrDefault(normBigram, normBigram);
                if (SKILLS_SET.contains(canonical)) found.add(canonical);
                else found.add(normBigram);
            }
            // also check glued form e.g., springboot
            String glued = (tokens.get(i) + tokens.get(i + 1)).replaceAll("\\s+","");
            String normGlued = normalizeToken(glued);
            if (SKILLS_SET.contains(normGlued)) {
                String canonical = SYNONYMS.getOrDefault(normGlued, normGlued);
                if (SKILLS_SET.contains(canonical)) found.add(canonical);
                else found.add(normGlued);
            }
        }

        // Final normalization mapping (e.g., mysql -> sql)
        Set<String> finalSet = new LinkedHashSet<>();
        for (String s : found) {
            String mapped = SYNONYMS.getOrDefault(s, s);
            finalSet.add(mapped);
        }
        return finalSet;
    }

    /**
     * Remove single-word tokens that are part of a multi-word skill.
     * Example: if set contains "spring boot" and "spring", remove "spring".
     */
    private Set<String> consolidateSkills(Set<String> skills) {
        Set<String> result = new LinkedHashSet<>(skills);
        // For each multi-word skill, remove contained single-word tokens if present
        for (String s : new ArrayList<>(skills)) {
            if (s.contains(" ")) {
                String[] parts = s.split("\\s+");
                for (String p : parts) {
                    // if the single-word exists and it is not identical to the multi-word, remove it
                    if (result.contains(p) && !p.equals(s)) {
                        result.remove(p);
                    }
                }
            }
        }
        return result;
    }

    public Map<String, Object> checkResume(String resumeText, String jobDescription) {
        // Build skill sets using the same skill extraction logic for both sides
        Set<String> resumeSkills = extractSkillsFromJob(resumeText); // detects multi-word skills too
        Set<String> jobSkills = extractSkillsFromJob(jobDescription);

        // Consolidate to avoid double counting (e.g., "spring" + "spring boot")
        resumeSkills = consolidateSkills(resumeSkills);
        jobSkills = consolidateSkills(jobSkills);

        // Intersection
        Set<String> matched = new HashSet<>(resumeSkills);
        matched.retainAll(jobSkills);

        Set<String> missing = new HashSet<>(jobSkills);
        missing.removeAll(matched);

        int jobCount = jobSkills.size();
        double score = jobCount == 0 ? 0.0 : (100.0 * matched.size() / jobCount);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("score", Math.round(score));
        result.put("matched", matched);
        result.put("missing", missing);
        result.put("jobKeywordsCount", jobCount);
        return result;
    }
}


