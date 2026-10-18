package com.dit.tracker.swing;

import com.dit.tracker.dao.ProjectDAO;
import com.dit.tracker.model.Proposal;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FacultyDashboardFrame extends JFrame {
    private final ProjectDAO dao = new ProjectDAO();
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField remarksField = new JTextField();
    private final JSpinner marksSpinner = new JSpinner(new SpinnerNumberModel(80, 0, 100, 1));

    public FacultyDashboardFrame() {
        setTitle("DIT University — Faculty Project Evaluation Command Center (Swing + JDBC)");
        setSize(1020, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(12, 12));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(15, 23, 42));
        header.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 18));
        JLabel title = new JLabel("FACULTY PROJECT EVALUATION & MILESTONE TRACKER");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(56, 189, 248));
        JButton refreshBtn = new JButton("⟳ Sync Live Database");
        refreshBtn.addActionListener(e -> loadProposalsAsync());
        header.add(title, BorderLayout.WEST);
        header.add(refreshBtn, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        String[] cols = {"ID", "SAP ID", "Student Name", "Project Title", "Domain", "Phase", "Progress %", "Status", "Marks"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(28);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new GridLayout(2, 1, 8, 8));
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 16, 14, 16));
        JPanel inputRow = new JPanel(new BorderLayout(10, 0));
        inputRow.add(new JLabel("Faculty Remarks: "), BorderLayout.WEST);
        inputRow.add(remarksField, BorderLayout.CENTER);
        JPanel marksBox = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        marksBox.add(new JLabel("Marks (0-100):"));
        marksBox.add(marksSpinner);
        inputRow.add(marksBox, BorderLayout.EAST);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton approveBtn = new JButton("✔ Approve Proposal");
        approveBtn.setBackground(new Color(22, 163, 74));
        approveBtn.setForeground(Color.WHITE);
        JButton revBtn = new JButton("⚠ Request Revision");
        JButton rejectBtn = new JButton("✖ Reject Proposal");
        rejectBtn.setBackground(new Color(220, 38, 38));
        rejectBtn.setForeground(Color.WHITE);
        approveBtn.addActionListener(e -> updateSelected("APPROVED"));
        revBtn.addActionListener(e -> updateSelected("REVISION"));
        rejectBtn.addActionListener(e -> updateSelected("REJECTED"));
        btnRow.add(approveBtn);
        btnRow.add(revBtn);
        btnRow.add(rejectBtn);
        bottom.add(inputRow);
        bottom.add(btnRow);
        add(bottom, BorderLayout.SOUTH);
        loadProposalsAsync();
    }

    private void updateSelected(String status) {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a proposal first.");
            return;
        }
        int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
        int marks = (Integer) marksSpinner.getValue();
        String remarks = remarksField.getText().isBlank() ? "Updated by faculty coordinator." : remarksField.getText().trim();
        dao.updateEvaluation(id, status, remarks, marks);
        loadProposalsAsync();
    }

    private void loadProposalsAsync() {
        new SwingWorker<List<Proposal>, Void>() {
            protected List<Proposal> doInBackground() {
                return dao.getAllProposals();
            }
            protected void done() {
                try {
                    tableModel.setRowCount(0);
                    for (Proposal p : get()) {
                        tableModel.addRow(new Object[]{p.getProposalId(), p.getStudentSapId(), p.getStudentName(), p.getTitle(), p.getDomain(), p.getCurrentPhase(), p.getCompletionPct(), p.getStatus(), p.getMarksAwarded()});
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(FacultyDashboardFrame.this, "Could not load proposals. Check MySQL and DBConnection.java.");
                }
            }
        }.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            } catch (Exception ignored) {}
            new FacultyDashboardFrame().setVisible(true);
        });
    }
}
