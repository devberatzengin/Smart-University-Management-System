package UserPackage;

public class Student extends User {
    public Student(String userID, String firstName, String lastName, String password, UserRole role,String email) {
        super(userID, firstName, lastName, password, UserRole.Student, email);
    }


}
