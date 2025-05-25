package repository;

import java.sql.*;

public class UserRepositoryImplement_Lv3 implements UserRepository_Lv3 {

    @Override
    public User_Lv3 findByEmail(String email) {
        String sql = "SELECT user_id, name, email FROM user WHERE email = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User_Lv3(
                            rs.getInt("user_id"),
                            rs.getString("name"),
                            rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("작성자 조회 실패", e);
        }
        return null;
    }

    @Override
    public int save(User_Lv3 user) {
        String sql = "INSERT INTO user (name, email, created_at, modified_at) "
                + "VALUES (?, ?, NOW(), NOW())";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
                else throw new RuntimeException("작성자 ID 생성 실패");
            }
        } catch (SQLException e) {
            throw new RuntimeException("작성자 저장 실패", e);
        }
    }
}
