<%@ page import="com.dit.tracker.model.Proposal" contentType="text/html;charset=UTF-8" %>
<%
    Proposal p = (Proposal) request.getAttribute("proposal");
    boolean hasProp = p != null;
    String status = hasProp ? p.getStatus() : "NOT_SUBMITTED";
    String badgeClass = "APPROVED".equals(status) ? "bg-success" : ("REJECTED".equals(status) ? "bg-danger" : ("REVISION".equals(status) ? "bg-info text-dark" : "bg-warning text-dark"));
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Student Project Workspace | DIT University</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body{background:#0f172a;color:#e2e8f0;min-height:100vh}
        .panel{background:#1e293b;border:1px solid #334155;border-radius:16px}
        .muted{color:#94a3b8}
    </style>
</head>
<body>
<nav class="navbar navbar-dark border-bottom border-secondary">
    <div class="container">
        <span class="navbar-brand fw-bold">DIT Project Tracker</span>
        <form action="portal" method="post"><input type="hidden" name="action" value="logout"><button class="btn btn-outline-light btn-sm">Log out</button></form>
    </div>
</nav>
<main class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-4 flex-wrap gap-2">
        <div><h2 class="fw-bold">Student Project Workspace</h2><p class="muted mb-0">Submit your proposal and track faculty feedback.</p></div>
        <span class="badge <%= badgeClass %> fs-6"><%= status %></span>
    </div>
    <% if (hasProp) { %>
    <div class="panel p-4 mb-4">
        <div class="d-flex justify-content-between gap-3 flex-wrap"><h4><%= p.getTitle() %></h4><strong><%= p.getCompletionPct() %>% complete</strong></div>
        <div class="progress my-3" role="progressbar" aria-valuenow="<%= p.getCompletionPct() %>" aria-valuemin="0" aria-valuemax="100"><div class="progress-bar" style="width:<%= p.getCompletionPct() %>%"></div></div>
        <p class="muted mb-1">Domain: <%= p.getDomain() %></p>
        <p><%= p.getAbstractText() %></p>
        <p class="mb-1">Current phase: <strong><%= p.getCurrentPhase() %></strong></p>
        <p class="mb-1">Faculty remarks: <%= p.getFacultyRemarks() %></p>
        <p class="mb-0">Marks awarded: <strong><%= p.getMarksAwarded() %>/100</strong></p>
        <p class="mt-2 mb-0"><a href="<%= p.getGithubUrl() %>" target="_blank" rel="noopener">GitHub repository</a></p>
    </div>
    <% } else { %>
    <div class="alert alert-info">You have not submitted a project proposal yet.</div>
    <% } %>
    <div class="panel p-4">
        <h4 class="mb-3"><%= hasProp ? "Update Project Proposal" : "Submit Project Proposal" %></h4>
        <form action="portal" method="post">
            <input type="hidden" name="action" value="save">
            <div class="mb-3"><label class="form-label">Project title</label><input class="form-control" name="title" required value="<%= hasProp ? p.getTitle() : "" %>"></div>
            <div class="mb-3"><label class="form-label">Domain</label><input class="form-control" name="domain" required value="<%= hasProp ? p.getDomain() : "" %>"></div>
            <div class="mb-3"><label class="form-label">Abstract</label><textarea class="form-control" name="abstractText" rows="4" required><%= hasProp ? p.getAbstractText() : "" %></textarea></div>
            <div class="mb-3"><label class="form-label">GitHub URL</label><input class="form-control" type="url" name="githubUrl" value="<%= hasProp ? p.getGithubUrl() : "https://github.com/" %>"></div>
            <div class="mb-3"><label class="form-label">Current phase</label><select class="form-select" name="currentPhase">
                <% String phase = hasProp ? p.getCurrentPhase() : "Phase 1: Synopsis Submitted"; String[] phases = {"Phase 1: Synopsis Submitted","Phase 2: Design & Planning","Phase 3: Mid-Term Prototype","Phase 4: Final Submission"}; for(String option:phases) { %>
                <option <%= option.equals(phase) ? "selected" : "" %>><%= option %></option>
                <% } %>
            </select></div>
            <div class="mb-4"><label class="form-label">Completion percentage (0–100)</label><input class="form-control" type="number" name="completionPct" min="0" max="100" value="<%= hasProp ? p.getCompletionPct() : 15 %>" required></div>
            <button class="btn btn-primary fw-semibold">Save Proposal & Progress</button>
        </form>
    </div>
</main>
</body>
</html>
