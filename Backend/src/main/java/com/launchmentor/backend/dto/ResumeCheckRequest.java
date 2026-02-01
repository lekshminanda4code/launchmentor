package com.launchmentor.backend.dto;

public class ResumeCheckRequest {
    private String resumeText;
    private String jobDescription;
    private Long careerId; // optional

    public ResumeCheckRequest() {}

    public String getResumeText() { return resumeText; }
    public void setResumeText(String resumeText) { this.resumeText = resumeText; }

    public String getJobDescription() { return jobDescription; }
    public void setJobDescription(String jobDescription) { this.jobDescription = jobDescription; }

    public Long getCareerId() { return careerId; }
    public void setCareerId(Long careerId) { this.careerId = careerId; }
}
