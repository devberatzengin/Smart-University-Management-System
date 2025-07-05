package UserPackage;

public class Lecturer extends User {
    public Lecturer(String userID, String firstName, String lastName, String password, UserRole role, String email) {
        super(userID, firstName, lastName, password, UserRole.Lecturer , email);
    }
}
