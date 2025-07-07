package UserPackage;

import Conn.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class User {
    protected String userID;
    protected String firstName;
    protected String lastName;
    protected String password;
    protected UserRole role;
    protected String email;

    public User(String userID, String firstName, String lastName, String password, UserRole role, String email) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public void forgotPassword(String email, String newPassword) {
        try {
            ResultSet rs = DBConnection.executeQuery("select UserID from tblUser where Email = ?", email);

            if (rs.next()) {
                String foundID = rs.getString("UserID");

                if (foundID.equals(this.userID)) {
                    int updated = DBConnection.executeUpdate(
                            "update tblUser set Userpassword = ? where UserID = ?", newPassword, this.userID);

                    if (updated > 0) {
                        System.out.println("Password succesfully updated!");
                    } else {
                        System.out.println("Password could't be updated!.");
                    }
                } else {
                    System.out.println("It's not your email! 👮‍♂️");
                }

                rs.getStatement().getConnection().close(); // düzgün kapatma
            } else {
                System.out.println("This email doesn't exist!");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //It's ready for students, but staff can also receive the announcement, so we need to make it class specific.
    public void viewMyAnnouncements() {
        try {
            List<Map<String, Object>> courseCodes = Conn.DBConnection.fetchAll(
                    "select CourseCode from tblCourses where CourseID IN " +
                            "(select CourseID from tblCourseByStudent where StudentID = ?)",
                    this.getUserID()
            );

            for (Map<String, Object> course : courseCodes) {
                String courseCode = (String) course.get("CourseCode");

                List<Map<String, Object>> announcements = Conn.DBConnection.fetchAll(
                        "select * from tblAnnouncements where CourseCode = ? or CourseCode = 'For Everyone'",
                        courseCode
                );

                for (Map<String, Object> a : announcements) {
                    System.out.println(" Title: " + a.get("Title"));
                    System.out.println(" Content: " + a.get("Content"));
                    System.out.println(" CourseCode: " + a.get("CourseCode"));
                    System.out.println(" Posted By: " + a.get("PostedBy"));
                    System.out.println(" Post Date: " + a.get("PostDate"));
                    System.out.println("----------------------------------");
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + " when listing announcements.");
        }
    }

    public String getUserID() {
        return userID;
    }
    public String getPassword() {
        return password;
    }
    public UserRole getRole() {
        return role;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

}
