package schedulemanager.repository;

public interface UserRepository_Lv3 {
    User_Lv3 findByEmail(String email);
    int save(User_Lv3 user);
}