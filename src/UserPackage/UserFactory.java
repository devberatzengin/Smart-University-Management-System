package UserPackage;

public class UserFactory {
    public static User createUser(String userID, String firstName, String lastName, String password, UserRole role,String email) {
        return switch (role) {
            case Admin -> new Admin(userID, firstName, lastName, password, UserRole.Admin, email);
            case Lecturer -> new Lecturer(userID, firstName, lastName, password, UserRole.Lecturer, email);
            case Staff -> new Staff(userID, firstName, lastName, password, UserRole.Staff, email);
            case Student -> new Student(userID, firstName, lastName, password,UserRole.Student, email);
            default -> null;
        };
    }
}
