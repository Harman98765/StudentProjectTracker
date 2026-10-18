package com.dit.tracker.model;

public class Proposal {
    private int proposalId;
    private String studentSapId;
    private String studentName;
    private String title;
    private String domain;
    private String abstractText;
    private String githubUrl;
    private String currentPhase;
    private int completionPct;
    private String status;
    private String facultyRemarks;
    private int marksAwarded;

    public Proposal() {}
    public int getProposalId() { return proposalId; }
    public void setProposalId(int proposalId) { this.proposalId = proposalId; }
    public String getStudentSapId() { return studentSapId; }
    public void setStudentSapId(String studentSapId) { this.studentSapId = studentSapId; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDomain() { return domain; }
    public void setDomain(String domain) { this.domain = domain; }
    public String getAbstractText() { return abstractText; }
    public void setAbstractText(String abstractText) { this.abstractText = abstractText; }
    public String getGithubUrl() { return githubUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }
    public String getCurrentPhase() { return currentPhase; }
    public void setCurrentPhase(String currentPhase) { this.currentPhase = currentPhase; }
    public int getCompletionPct() { return completionPct; }
    public void setCompletionPct(int completionPct) { this.completionPct = completionPct; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getFacultyRemarks() { return facultyRemarks; }
    public void setFacultyRemarks(String facultyRemarks) { this.facultyRemarks = facultyRemarks; }
    public int getMarksAwarded() { return marksAwarded; }
    public void setMarksAwarded(int marksAwarded) { this.marksAwarded = marksAwarded; }
}
