package com.dit.tracker.dao;

import com.dit.tracker.config.DBConnection;
import com.dit.tracker.model.Proposal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {
    public boolean validateLogin(String sapId, String password) {
        String sql = "SELECT 1 FROM students WHERE sap_id = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sapId);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Proposal getProposalBySap(String sapId) {
        String sql = "SELECT p.*, s.full_name FROM proposals p JOIN students s ON p.student_sap_id = s.sap_id WHERE p.student_sap_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sapId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Proposal> getAllProposals() {
        List<Proposal> list = new ArrayList<>();
        String sql = "SELECT p.*, s.full_name FROM proposals p JOIN students s ON p.student_sap_id = s.sap_id ORDER BY p.updated_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void saveOrUpdateProposal(String sapId, String title, String domain, String abstractTxt, String github, String phase, int pct) {
        String sql = "INSERT INTO proposals (student_sap_id, title, domain, abstract_text, github_url, current_phase, completion_pct) VALUES (?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE title=VALUES(title), domain=VALUES(domain), abstract_text=VALUES(abstract_text), github_url=VALUES(github_url), current_phase=VALUES(current_phase), completion_pct=VALUES(completion_pct)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sapId);
            ps.setString(2, title);
            ps.setString(3, domain);
            ps.setString(4, abstractTxt);
            ps.setString(5, github);
            ps.setString(6, phase);
            ps.setInt(7, pct);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEvaluation(int proposalId, String status, String remarks, int marks) {
        String sql = "UPDATE proposals SET status = ?, faculty_remarks = ?, marks_awarded = ? WHERE proposal_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, remarks);
            ps.setInt(3, marks);
            ps.setInt(4, proposalId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Proposal mapRow(ResultSet rs) throws SQLException {
        Proposal p = new Proposal();
        p.setProposalId(rs.getInt("proposal_id"));
        p.setStudentSapId(rs.getString("student_sap_id"));
        p.setStudentName(rs.getString("full_name"));
        p.setTitle(rs.getString("title"));
        p.setDomain(rs.getString("domain"));
        p.setAbstractText(rs.getString("abstract_text"));
        p.setGithubUrl(rs.getString("github_url"));
        p.setCurrentPhase(rs.getString("current_phase"));
        p.setCompletionPct(rs.getInt("completion_pct"));
        p.setStatus(rs.getString("status"));
        p.setFacultyRemarks(rs.getString("faculty_remarks"));
        p.setMarksAwarded(rs.getInt("marks_awarded"));
        return p;
    }
}
