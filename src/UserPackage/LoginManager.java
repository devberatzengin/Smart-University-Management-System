package UserPackage;
import Conn.DBConnection;
import java.sql.ResultSet;

public class LoginManager {
    public static User login(String userID, String password) {
        try {
            ResultSet rs = DBConnection.executeQuery(
                    "select * from Users where userID = ? and password = ?", userID, password
            );

            if (rs.next()) {
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String roleStr = rs.getString("UserRole");
                String email = rs.getString("Email");

                UserRole role = UserRole.valueOf(roleStr); // String → Enum
                rs.getStatement().getConnection().close();

                return UserFactory.createUser(userID, firstName, lastName, password, role , email);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
