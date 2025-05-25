package schedulemanager.repository;

public class User_Lv3 {
    private int userId;
    private String name;
    private String email;

    public User_Lv3() {}

    public User_Lv3(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User_Lv3(int userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}
