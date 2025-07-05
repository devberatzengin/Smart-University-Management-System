package UserPackage;
import Conn.DBConnection;

import java.sql.SQLException;


public class Admin extends User {

    public Admin(String userID, String firstName, String lastName, String password, UserRole role, String email) {
        super(userID, firstName, lastName, password, UserRole.Admin, email);
    }

    public boolean createNewUser(String firstName, String lastName, String password, UserRole role, String email) {
        try {
            int intRole = switch (role) {
                case Admin -> 3;
                case Lecturer -> 2;
                case Staff -> 1;
                case Student -> 0;
                default -> throw new IllegalArgumentException("Invalid user role: " + role);
            };

            int result = DBConnection.executeUpdate(
                    "insert into tblUser (Firstname, Lastname, UserPassword, UserRole, Email) VALUES (?, ?, ?, ?, ?)",
                    firstName, lastName, password, intRole, email
            );

            if (result > 0) {
                System.out.println(result + " user created successfully.");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return false;
    }

}
