import UserPackage.*;

public class Main {
    public static void main(String[] args) {
        User user = LoginManager.login("111222333@cbu.edu.tr","adminpass");

        if (user != null) {
            if (user instanceof Admin admin)
            {
                admin.createNewUser("Testo", "Taylan", "kadikoyBogasi", UserRole.Student, "000111222@cbu.edu.tr");
            }

            else if (user instanceof Lecturer lecturer)
            {

            }

            else if (user instanceof Student)
            {

            }

            else if (user instanceof Staff staff)
            {

            }

        }else{
            System.out.println("Error: User not found");
        }
    }
}
