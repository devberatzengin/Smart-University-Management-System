package UserPackage;
import Conn.DBConnection;

import java.sql.Connection;
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

    // its have to fix because maybe this student doesn't get this course
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

    public void viewStudentInCourse(String CourseCode) {
        try {
            String query = """
            select u.UserID, u.Firstname, u.Lastname, u.Email
            from tblUser u
            join tblCourseByStudent cbs ON u.UserID = cbs.StudentID
            join tblCourses c ON c.CourseID = cbs.CourseID
            where c.CourseCode = ?
        """;

            ResultSet rs = Conn.DBConnection.executeQuery(query, CourseCode);
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("UserID") + " | " +
                        rs.getString("Firstname") + " " +
                        rs.getString("Lastname") + " | " +
                        rs.getString("Email"));
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            System.out.println("Error: " + e + "\nError in view student in courses.");
        }
    }

    public void makeAnnouncement(String title, String content, String courseCode) {
        try {
            ResultSet rs = Conn.DBConnection.executeQuery("select LecturerID from tblCourses where CourseCode = ?", courseCode);
            while (rs.next()) {
                if (!rs.getString("LecturerID").equals(this.getUserID())) {
                    System.out.println("You're not the lecturer of this course!");
                }else {
                    int result = Conn.DBConnection.executeUpdate(
                            "insert into tblAnnouncements (Title,Content,CourseCode,PostedBy,PostDate) " +
                                    " values (?,?,?,?,?)",title,content,courseCode,this.getFirstName()+" "+this.getLastName(),LocalDate.now());
                    if (result >0) {
                        System.out.println(result+" announcement has been added!");
                    }else {
                        System.out.println(result+" announcement has not been added!");
                    }
                }
            }
            rs.getStatement().getConnection().close();
        }catch (Exception e) {
            System.out.println("Error: " + e + "\nError when creating announcement.");
        }
    }

    public void makeAnnouncement(String title, String content) {
        try {
            int result = Conn.DBConnection.executeUpdate(
                    "insert into tblAnnouncements (Title,Content,CourseCode,PostedBy,PostDate) " +
                            " values (?,?,?,?,?)",title,content,"For Everyone",this.getFirstName()+" "+this.getLastName(),LocalDate.now());
            if (result >0) {
                System.out.println(result+" announcement has been added!");
            }else {
                System.out.println(result+" announcement has not been added!");
            }

        }catch (Exception e) {
            System.out.println("Error: " + e + "\nError when creating announcement.");
        }
    }



}
