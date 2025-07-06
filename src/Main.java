import UserPackage.*;

public class Main {
    public static void main(String[] args) {
        User user = LoginManager.login("777888999@cbu.edu.tr","pass");

        if (user != null) {
            if (user instanceof Admin admin)
            {
                //admin.createNewUser("Testo", "Taylan", "kadikoyBogasi", UserRole.Student, "000111222@cbu.edu.tr");
            }

            else if (user instanceof Lecturer lecturer)
            {
                //lecturer.viewAssignedCourses();
                //lecturer.enterGrade("51","YZM2202",82,80);
                lecturer.viewStudentInCourse("YZM4400");
            }

            else if (user instanceof Student student)
            {
                //student.forgotPassword("000111222@cbu.edu.tr","password");
            }

            else if (user instanceof Staff staff)
            {

            }

        }else{
            System.out.println("Error: User not found");
        }
    }
}
