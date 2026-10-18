DROP DATABASE IF EXISTS dit_project_tracker;
CREATE DATABASE dit_project_tracker;
USE dit_project_tracker;

CREATE TABLE students (
    sap_id VARCHAR(20) PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(64) NOT NULL,
    batch VARCHAR(30) DEFAULT 'B.Tech CSE 5th Sem'
);

CREATE TABLE proposals (
    proposal_id INT AUTO_INCREMENT PRIMARY KEY,
    student_sap_id VARCHAR(20) UNIQUE NOT NULL,
    title VARCHAR(200) NOT NULL,
    domain VARCHAR(80) NOT NULL,
    abstract_text TEXT NOT NULL,
    github_url VARCHAR(255) DEFAULT 'https://github.com/',
    current_phase VARCHAR(60) DEFAULT 'Phase 1: Synopsis Submitted',
    completion_pct INT DEFAULT 15 CHECK (completion_pct BETWEEN 0 AND 100),
    status ENUM('PENDING', 'APPROVED', 'REJECTED', 'REVISION') DEFAULT 'PENDING',
    faculty_remarks VARCHAR(255) DEFAULT 'Awaiting faculty coordinator evaluation.',
    marks_awarded INT DEFAULT 0,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (student_sap_id) REFERENCES students(sap_id) ON DELETE CASCADE
);

INSERT INTO students (sap_id, full_name, email, password) VALUES
('1000015421', 'Aarav Sharma', 'aarav.1000015421@dit.edu.in', 'dit123'),
('1000015488', 'Priya Rawat', 'priya.1000015488@dit.edu.in', 'dit123'),
('1000015502', 'Rohan Negi', 'rohan.1000015502@dit.edu.in', 'dit123');

INSERT INTO proposals (student_sap_id, title, domain, abstract_text, github_url, current_phase, completion_pct, status, faculty_remarks, marks_awarded) VALUES
('1000015421', 'AI-Driven Smart Traffic Signal Controller for Dehradun', 'AI & Computer Vision',
 'Uses YOLOv8 object detection to dynamically adjust green-light duration at Clock Tower & ISBT intersections.',
 'https://github.com/aarav-dit/smart-traffic', 'Phase 3: Mid-Term Prototype', 65, 'APPROVED',
 'Strong societal impact. Proceed with dataset training.', 84),
('1000015488', 'Blockchain Academic Certificate Verification Portal', 'Cybersecurity & Web3',
 'Tamper-proof degree issuance and QR verification using SHA-256 merkle trees and Java backend.',
 'https://github.com/priya-dit/cert-verify', 'Phase 1: Synopsis Submitted', 20, 'PENDING',
 'Awaiting faculty coordinator evaluation.', 0);
