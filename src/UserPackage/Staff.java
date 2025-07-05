package UserPackage;

public class Staff extends User {
    public Staff(String userID, String firstName, String lastName, String password, UserRole role, String email) {
        super(userID, firstName, lastName, password, UserRole.Staff,  email);
    }
}
