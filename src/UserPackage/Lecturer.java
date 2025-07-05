package UserPackage;
import Conn.DBConnection;

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


}
