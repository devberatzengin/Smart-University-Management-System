package UserPackage;

import Conn.DBConnection;

import java.sql.ResultSet;

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
