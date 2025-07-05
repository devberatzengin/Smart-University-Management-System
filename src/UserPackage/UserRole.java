package UserPackage;

public enum UserRole {
    Student(0),
    Staff(1),
    Lecturer(2),
    Admin(3);

    private final int value;

    UserRole(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static UserRole fromInt(int i) {
        for (UserRole role : UserRole.values()) {
            if (role.getValue() == i) return role;
        }
        throw new IllegalArgumentException("No enum constant for value " + i);
    }
}

