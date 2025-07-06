package UserPackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class Student extends User {
    public Student(String userID, String firstName, String lastName, String password, UserRole role,String email) {
        super(userID, firstName, lastName, password, UserRole.Student, email);
    }

    public void viewEnrolledCourses() {
        try {
            ResultSet rs = Conn.DBConnection.executeQuery(
                    "select c.CourseID, c.CourseCode, c.CourseName, c.Semester, c.Year, " +
                        "u.Firstname AS LecturerFirstName, u.Lastname AS LecturerLastName " +
                        "from tblCourseByStudent cbs " +
                        "join tblCourses c ON cbs.CourseID = c.CourseID " +
                        "join tblUser u ON c.LecturerID = u.UserID " +
                        "where cbs.StudentID = ?",
                    this.getUserID()
            );
            rs.getStatement().getConnection().close();

            while (rs.next()) {
                System.out.println(
                        "CourseCode: " + rs.getString("CourseCode") +
                        " | CourseName: " + rs.getString("CourseName") +
                        " | Semester: " + rs.getString("Semester") +
                        " | Year: " + rs.getString("Year") +
                        " | Lecturer: " + rs.getString("LecturerFirstName") + " " + rs.getString("LecturerLastName")
                );
            }

        } catch (Exception e) {
            System.out.println("Error: " + e + "\nError when viewing " + this.getFirstName() + "'s courses.");
        }
    }



}
