package UserPackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Map;

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

            while (rs.next()) {
                System.out.println(
                        "CourseCode: " + rs.getString("CourseCode") +
                        " | CourseName: " + rs.getString("CourseName") +
                        " | Semester: " + rs.getString("Semester") +
                        " | Year: " + rs.getString("Year") +
                        " | Lecturer: " + rs.getString("LecturerFirstName") + " " + rs.getString("LecturerLastName")
                );
            }
            //Bug fixed closing operations have to last line
            rs.getStatement().getConnection().close();

        } catch (Exception e) {
            System.out.println("Error: " + e + "\nError when viewing " + this.getFirstName() + "'s courses.");
        }
    }

    public void viewGrades(String courseCode) {
        try {
            String sql = """
                select g.CourseCode, g.Midterm, g.Final, g.MakeupExam, g.Semester, g.Year,
                       g.Average, g.GradeLetter, u.Firstname AS LecturerFirstName, u.Lastname AS LecturerLastName
                from tblCourseGrades g
                join tblCourses c ON g.CourseCode = c.CourseCode
                join tblUser u ON g.LecturerID = u.UserID
                where g.StudentID = ? AND g.CourseCode = ?
                """;

            List<Map<String, Object>> results = Conn.DBConnection.fetchAll(sql, this.getUserID(), courseCode);

            if (results.isEmpty()) {
                System.out.println("No grades found for " + courseCode +"\nWrong CourseCode or You dont get this Course Yet.");
            }

            for (Map<String, Object> row : results) {
                System.out.println("CourseCode: " + row.get("CourseCode") +
                        " | Midterm: " + row.get("Midterm") +
                        " | Final: " + row.get("Final") +
                        " | MakeupExam: " + row.get("MakeupExam") +
                        " | Semester: " + row.get("Semester") +
                        " | Year: " + row.get("Year") +
                        " | Average: " + row.get("Average") +
                        " | GradeLetter: " + row.get("GradeLetter") +
                        " | Lecturer: " + row.get("LecturerFirstName") + " " + row.get("LecturerLastName")
                );
            }

        } catch (Exception e) {
            System.out.println("Error: " + e + "\nError when viewing " + this.getFirstName() + "'s " + courseCode + "'s grades.");
        }
    }

    public void viewAllGrades() {
        try {
            ResultSet rs = Conn.DBConnection.executeQuery("""
            select DISTINCT c.CourseCode
            from tblCourseByStudent s
            join tblCourses c ON s.CourseID = c.CourseID
            where s.StudentID = ?
        """, this.getUserID());

            while (rs.next()) {
                this.viewGrades(rs.getString("CourseCode"));
            }

            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            System.out.println("Error: " + e + "\nError when viewing " + this.getFirstName() + "'s all grades.");
        }
    }

    public void enrollToCourse(String courseCode) {
        try {

            ResultSet rs = Conn.DBConnection.executeQuery("select CourseID from tblCourses where CourseCode = ?", courseCode);

            if (rs.next()) {
                int courseId = rs.getInt("CourseID");

                int result = Conn.DBConnection.executeUpdate("insert into tblCourseByStudent (StudentID, CourseID) values (?, ?)",this.getUserID(), courseId);

                if (result > 0) {
                    System.out.println("Successfully enrolled in course: " + courseCode);
                } else {
                    System.out.println("Failed to enroll in course: " + courseCode);
                }

                rs.getStatement().getConnection().close();
            } else {
                System.out.println("Course not found with code: " + courseCode);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e + "\nError when enrolling to " + this.getFirstName() + "'s course: " + courseCode);
        }
    }

    public void unEnrollToCourse(String courseCode) {
        try {
            ResultSet rs = Conn.DBConnection.executeQuery("select CourseID from tblCourses where CourseCode = ?", courseCode);
            if (rs.next()) {
                int result = Conn.DBConnection.executeUpdate("delete from tblCourseByStudent where CourseID = ? and StudentID = ?",rs.getString("CourseID") ,this.getUserID());
                if (result > 0) {
                    System.out.println("Successfully unenrolled in "+this.getFirstName()+"' course: " + courseCode);
                }else  {
                    System.out.println("Failed to unenroll in"+this.getFirstName()+"'s course: " + courseCode+"\n Maybe This user doesn't have this course.");
                }
            }else {
                System.out.println("Course not found with code: " + courseCode);
            }
            rs.getStatement().getConnection().close();
        }catch (Exception e) {
            System.out.println("Error: " + e + "\nError when unenrolling " + this.getFirstName() + "'s  course: "+courseCode);
        }
    }

}
