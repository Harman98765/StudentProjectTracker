package com.dit.tracker.controller;

import com.dit.tracker.dao.ProjectDAO;
import com.dit.tracker.model.Proposal;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/portal")
public class StudentPortalServlet extends HttpServlet {
    private final ProjectDAO dao = new ProjectDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("sapId") == null) {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        String sapId = (String) session.getAttribute("sapId");
        request.setAttribute("proposal", dao.getProposalBySap(sapId));
        request.getRequestDispatcher("/student_dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("login".equals(action)) {
            String sapId = request.getParameter("sapId");
            String password = request.getParameter("password");
            if (dao.validateLogin(sapId, password)) {
                request.getSession(true).setAttribute("sapId", sapId);
                response.sendRedirect(request.getContextPath() + "/portal");
            } else {
                request.setAttribute("error", "Invalid SAP ID or password, or database connection failed.");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
            return;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("sapId") == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        if ("save".equals(action)) {
            String sapId = (String) session.getAttribute("sapId");
            String title = request.getParameter("title");
            String domain = request.getParameter("domain");
            String abstractText = request.getParameter("abstractText");
            String github = request.getParameter("githubUrl");
            String phase = request.getParameter("currentPhase");
            int pct;
            try {
                pct = Integer.parseInt(request.getParameter("completionPct"));
                pct = Math.max(0, Math.min(100, pct));
            } catch (Exception e) {
                pct = 15;
            }
            if (title != null && domain != null && abstractText != null && !title.isBlank() && !domain.isBlank() && !abstractText.isBlank()) {
                dao.saveOrUpdateProposal(sapId, title, domain, abstractText, github == null ? "" : github, phase == null ? "Phase 1: Synopsis Submitted" : phase, pct);
            }
            response.sendRedirect(request.getContextPath() + "/portal");
        } else if ("logout".equals(action)) {
            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            response.sendRedirect(request.getContextPath() + "/portal");
        }
    }
}
