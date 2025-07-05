package UserPackage;
import Conn.DBConnection;
import java.time.LocalDate;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public class Lecturer extends User {

    public Lecturer(String userID, String firstName, String lastName, String password, UserRole role, String email) {
        super(userID, firstName, lastName, password, UserRole.Lecturer , email);
    }

    public void viewAssignedCourses() {
        try {
            List<Map<String, Object>> myCourses = DBConnection.fetchAll("select * from tblCourses where LecturerID = ?",this.getUserID());
            for (Map<String, Object> map : myCourses) {
                System.out.println("CourseID: "+map.get("CourseID")
                        + " | CourseCode: "+map.get("CourseCode")
                        + " | CourseName: "+map.get("CourseName")
                        + " | Semester: "+map.get("Semester")
                        + " | Year: "+map.get("Year")
                        + " | Credit: " +map.get("Credit") );
                System.out.println();
            }
        }
        catch(Exception e) {
            System.out.println("Error: "+ e +"\n Error in view assigned courses.");
        }
    }

    public void enterGrade(String StudentId, String CourseCode, int midterm, int finalterm) {
        try {
            // Student is correct ?
            try (ResultSet studentCheck = DBConnection.executeQuery("select * from tblUser where UserID = ? and UserRole = 0", StudentId)) {
                if (!studentCheck.next()) {
                    System.out.println("Student with ID " + StudentId + " not found.");
                    return;
                }
            }

            // 2. Course is correct
            try (ResultSet courseCheck = DBConnection.executeQuery("select * from tblCourses where CourseCode = ?", CourseCode)) {
                if (!courseCheck.next()) {
                    System.out.println("Course with code " + CourseCode + " not found.");
                    return;
                }

                // 3. This Lecturer gives this course
                String lecturerFromDB = courseCheck.getString("LecturerID");
                if (!lecturerFromDB.equals(this.getUserID())) {
                    System.out.println("You're not the lecturer of this course!");
                    return;
                }
            }

            String semestertype;
            if (LocalDate.now().getMonthValue() >= 2 && LocalDate.now().getMonthValue() <= 6) {
                semestertype = "Spring";
            } else if (LocalDate.now().getMonthValue() >= 9 && LocalDate.now().getMonthValue() <= 12) {
                semestertype = "Fall";
            } else {
                semestertype = "NotNormal";
            }

            int result = DBConnection.executeUpdate(
                    "insert into tblCourseGrades (StudentID, CourseCode, Midterm, Final, LecturerID, Semester, Year) " +
                            "values (?, ?, ?, ?, ?, ?, ?)",
                    StudentId, CourseCode, midterm, finalterm, this.getUserID(), semestertype, LocalDate.now().getYear());

            System.out.println(result + " course grade inserted.");

        } catch (Exception e) {
            System.out.println("Error: " + e + "\nError in entering grade.");
        }
    }

}
