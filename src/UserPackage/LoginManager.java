package UserPackage;
import Conn.DBConnection;
import java.sql.ResultSet;

public class LoginManager {
    public static User login(String email, String password) {
        try {
            ResultSet rs = DBConnection.executeQuery(
                    "select * from tblUser where Email = ? and Userpassword = ?", email, password
            );

            if (rs.next()) {
                String firstName = rs.getString("Firstname");
                String lastName = rs.getString("Lastname");
                String roleStr = rs.getString("UserRole");
                String userID = rs.getString("UserID");

                int roleInt = Integer.parseInt(roleStr);
                UserRole role = UserRole.fromInt(roleInt);

                rs.getStatement().getConnection().close();

                return UserFactory.createUser(userID, firstName, lastName, password, role , email);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
