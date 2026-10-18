Student Project Proposal & Progress Tracking System
Based on the supplied six-page handbook. Missing/truncated code has been reconstructed to make the documented features runnable.

REQUIREMENTS
- JDK 17 or newer
- Maven (or IntelliJ's bundled Maven)
- MySQL Server 8.0 and MySQL Workbench
- Apache Tomcat 10.1

1. DATABASE
Open sql/schema.sql in MySQL Workbench and execute it. WARNING: the script drops and recreates dit_project_tracker.
Sample login: SAP ID 1000015421, password dit123.

2. DATABASE PASSWORD
Open src/main/java/com/dit/tracker/config/DBConnection.java.
Set PASSWORD to your MySQL root password. The default in this project is "root", matching the handbook example; it will not work unless your own MySQL password is root.

3. OPEN IN INTELLIJ
Open the StudentProjectTracker folder as a Maven project. Allow Maven to download dependencies.
Build the project using Maven: clean package.
The resulting WAR should be target/StudentProjectTracker.war.

4. DEPLOY WEB PORTAL
Copy target/StudentProjectTracker.war into Tomcat's webapps folder.
Start Tomcat using its bin/startup.bat.
Open http://localhost:8080/StudentProjectTracker/
Bootstrap requires internet access for its CDN stylesheet.

5. RUN FACULTY SWING APP
In IntelliJ, run com.dit.tracker.swing.FacultyDashboardFrame.
It uses the same MySQL database as the web portal.

NOTES
- The PDF does not include every source file in full. This project reconstructs the DAO, servlet, dashboard and missing Maven configuration to match the documented architecture and features.
- The sample passwords are plaintext because that is how the handbook schema defines them; this is for a local classroom demo, not production use.
- If port 8080 is occupied, configure Tomcat to use another port.
