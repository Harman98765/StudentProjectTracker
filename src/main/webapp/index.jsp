<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>DIT University | Project Portal Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body{background:radial-gradient(circle at top left,#0f172a,#1e1b4b);min-height:100vh;color:#f8fafc;display:flex;align-items:center}
        .glass-card{background:rgba(30,41,59,.78);backdrop-filter:blur(14px);border:1px solid rgba(255,255,255,.14);border-radius:16px}
    </style>
</head>
<body>
<div class="container" style="max-width:430px">
    <div class="glass-card p-4 shadow-lg">
        <span class="badge bg-primary mb-2">DIT UNIVERSITY • CSE 5TH SEM</span>
        <h3 class="fw-bold mb-1">Project Tracker Portal</h3>
        <p class="text-secondary small mb-4">Sign in with your University SAP ID</p>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger py-2 small"><%= request.getAttribute("error") %></div>
        <% } %>
        <form action="portal" method="post">
            <input type="hidden" name="action" value="login">
            <div class="mb-3">
                <label class="form-label small text-light">SAP ID</label>
                <input type="text" name="sapId" class="form-control bg-dark text-white border-secondary" value="1000015421" required>
            </div>
            <div class="mb-4">
                <label class="form-label small text-light">Password</label>
                <input type="password" name="password" class="form-control bg-dark text-white border-secondary" value="dit123" required>
            </div>
            <button type="submit" class="btn btn-primary w-100 fw-semibold py-2">Launch Student Workspace &rarr;</button>
        </form>
    </div>
</div>
</body>
</html>
